package com.example.springjwtauthentication.controller;

import com.example.springjwtauthentication.controller.response.HttpResponse;
import com.example.springjwtauthentication.entity.*;
import com.example.springjwtauthentication.mapper.ContentMapper;
import com.example.springjwtauthentication.model.ContentModel;
import com.example.springjwtauthentication.model.CourseModel;
import com.example.springjwtauthentication.repository.CourseRepository;
import com.example.springjwtauthentication.repository.StudentRepository;
import com.example.springjwtauthentication.repository.TeacherRepository;
import com.example.springjwtauthentication.service.CourseContentService;
import com.example.springjwtauthentication.service.CourseService;
import com.google.api.client.util.IOUtils;
import com.jlefebure.spring.boot.minio.MinioException;
import com.jlefebure.spring.boot.minio.MinioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Path;
import java.util.List;

import static com.example.springjwtauthentication.security.jwt.JwtHelper.getJwtUser;

@RestController
@RequestMapping("/api")
public class CourseContentController {

    @Autowired
    private MinioService minioService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseContentService courseContentService;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Operation(summary = "this api is to upload course content file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "failure", content = @io.swagger.v3.oas.annotations.media.Content)})
    @PreAuthorize("#teacherId.toString().equals(authentication.principal) " +
            "&& #authentication.getAuthorities().toArray()[0].toString().equals(\"teacher\")")
    @PostMapping(value = "/teachers/{teacherId}/courses/{courseId}/contents")
    public HttpResponse<String> uploadCourseContent(@RequestHeader("AUTHORIZATION") String header,
                                                    @PathVariable("teacherId") Long teacherId,
                                                    @PathVariable("courseId") Long courseId,
                                                    @RequestParam("file") MultipartFile file,
                                                    Authentication authentication) {

        HttpResponse<String> response = new HttpResponse<>();
        if (isUploadAllowed(header, courseId)) {
            Teacher teacher = teacherRepository.findTeacherById(teacherId);
            Course course = courseRepository.findCourseById(courseId);

            if (teacher.getCourses().contains(course)) {
                ContentModel content = ContentModel
                        .builder()
                        .fileName(file.getOriginalFilename())
                        .contentType(file.getContentType())
                        .description(String.valueOf(file.getSize()))
                        .build();

                Path path = Path.of(course.getCourseName() + "/" + file.getOriginalFilename());
                try {
                    minioService.upload(path, file.getInputStream(), file.getContentType());

                    Content contentEntity = ContentMapper.toEntity(content);
                    contentEntity.setCourse(course);
                    courseContentService.saveCourseContent(contentEntity);
                    course.getCourseContents().add(contentEntity);
                    courseRepository.save(course);
                    response.setSuccess(true);
                } catch (MinioException e) {
                    throw new IllegalStateException("The file cannot be upload on the internal storage. Please retry later", e);
                } catch (IOException e) {
                    throw new IllegalStateException("The file cannot be read", e);
                }
            } else {
                response.setSuccess(false);
                response.setMessage("upload failed");
            }
        } else {
            response.setMessage("Forbidden");
            throw new AccessDeniedException("403 :: Forbidden");
        }
        return response;
    }

    @Operation(summary = "this api is to get list of all course content files")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "failure", content = @io.swagger.v3.oas.annotations.media.Content)})
    @GetMapping("/courses/{courseId}/contents")
    public HttpResponse<List<ContentModel>> getAllCourseContentsList(@PathVariable("courseId") Long courseId,
                                                                     @RequestHeader("AUTHORIZATION") String header) {

        HttpResponse<List<ContentModel>> response = new HttpResponse<>();
        if (isDownloadAllowed(header, courseId)) {
            CourseModel course = courseService.findCourseById(courseId);
            response.setData(courseContentService.getCourseContents(course.getId()));
        } else {
            throw new AccessDeniedException("403:: forbidden");
        }
        response.setSuccess(response.getData() != null);
        return response;
    }

    @Operation(summary = "this api is to doenload a course content file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")})})
    @GetMapping("/courses/{courseId}/contents/{fileName}")
    public void downloadSingleContent(@PathVariable("courseId") Long courseId,
                                      @PathVariable("fileName") String fileName,
                                      @RequestHeader("AUTHORIZATION") String header,
                                      HttpServletResponse response) {

        if (isDownloadAllowed(header, courseId)) {
            InputStream inputStream = null;
            try {
                Course course = courseRepository.findCourseById(courseId);
                inputStream = minioService.get(Path.of(course.getCourseName() + "/" + fileName));
                // Set the content type and attachment header.
                response.addHeader("Content-disposition", "attachment;filename=" + fileName);
                response.setContentType(URLConnection.guessContentTypeFromName(fileName));
                // Copy the stream to the response's output stream.
                IOUtils.copy(inputStream, response.getOutputStream());
                response.flushBuffer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new AccessDeniedException("403 :: forbidden");
        }
    }

    private boolean isDownloadAllowed(String header, Long courseId) {
        boolean allowed = false;
        User user = teacherRepository.findTeacherById(getJwtUser(header));
        if(user == null){
            user = studentRepository.findStudentById(getJwtUser(header));
        }
        Course course = courseRepository.findCourseById(courseId);
        if (user.getRole().equals("student")) {
            Student student = studentRepository.findStudentByEmail(user.getEmail());
            if (student.getCourses().contains(course)) {
                allowed = true;
            }
        } else if (user.getRole().equals("teacher")) {
            Teacher teacher = teacherRepository.findTeacherByEmail(user.getEmail());
            if (teacher.getCourses().contains(course)) {
                allowed = true;
            }
        } else if (user.getRole().equals("admin")) {
            allowed = true;
        }
        return allowed;
    }

    private boolean isUploadAllowed(String header, Long courseId) {
        boolean allowed = false;
        User user = teacherRepository.findTeacherById(getJwtUser(header));
        Course course = courseRepository.findCourseById(courseId);
        if (user.getRole().equals("student")) {
            return false;
        } else if (user.getRole().equals("teacher")) {
            Teacher teacher = teacherRepository.findTeacherByEmail(user.getEmail());
            if (teacher.getCourses().contains(course)) {
                allowed = true;
            }
        } else if (user.getRole().equals("admin")) {
            allowed = true;
        }
        return allowed;
    }
}
