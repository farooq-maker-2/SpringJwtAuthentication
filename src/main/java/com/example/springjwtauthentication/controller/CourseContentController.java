package com.example.springjwtauthentication.controller;

import com.example.springjwtauthentication.annotations.IsValidTeacher;
import com.example.springjwtauthentication.entity.*;
import com.example.springjwtauthentication.model.ContentModel;
import com.example.springjwtauthentication.service.*;
import com.example.springjwtauthentication.view.response.HttpResponse;
import com.google.api.client.util.IOUtils;
import com.jlefebure.spring.boot.minio.MinioException;
import com.jlefebure.spring.boot.minio.MinioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.example.springjwtauthentication.security.jwt.JwtHelper.getUserIdFromJwt;
import static com.example.springjwtauthentication.security.jwt.JwtHelper.getUserRoleFromJwt;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class CourseContentController {

    private final MinioService minioService;
    private final CourseService courseService;
    private final CourseContentService courseContentService;
    private final TeacherService teacherService;
    private final StudentService studentService;
    private final AdminService adminService;

    @IsValidTeacher
    @RolesAllowed({"TEACHER"})
    @Operation(summary = "this api is to upload course content file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "400", description = "failure")})
    @PostMapping(path = "/teachers/{teacherId}/courses/{courseId}/contents", produces = "application/json")
    public HttpResponse<String> uploadCourseContent(@RequestHeader("AUTHORIZATION") String header,
                                                    @PathVariable("teacherId") Long teacherId,
                                                    @PathVariable("courseId") Long courseId,
                                                    @RequestParam("file") MultipartFile file) {

        HttpResponse<String> response = new HttpResponse<>();
        if (isUploadAllowed(header, courseId)) {
            Optional<Teacher> teacher = teacherService.findTeacherById(teacherId);
            Optional<Course> course = courseService.findCourseById(courseId);

            if (teacher.isPresent() && teacher.get().getCourses().contains(course.get())) {
                Content content = Content
                        .builder()
                        .fileName(file.getOriginalFilename())
                        .contentType(file.getContentType())
                        .description(String.valueOf(file.getSize()))
                        .build();

                try {
                    if (course.isPresent()) {
                        Path path = Path.of(course.get().getCourseName() + "/" + file.getOriginalFilename());
                        minioService.upload(path, file.getInputStream(), file.getContentType());
                        content.setCourse(course.get());
                        courseContentService.saveCourseContent(content);
                        course.get().getCourseContents().add(content);
                        courseService.saveCourse(course.get());
                        response.setSuccess(true);
                    }
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


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "400", description = "failure")})
    @Operation(summary = "this api is to get list of all course content files")
    @GetMapping(path = "/courses/{courseId}/contents", produces = "application/json")
    public HttpResponse<List<ContentModel>> getAllCourseContentsList(@PathVariable("courseId") Long courseId,
                                                                     @RequestHeader("AUTHORIZATION") String header) {

        HttpResponse<List<ContentModel>> response = new HttpResponse<>();
        if (isDownloadAllowed(header, courseId)) {
            Optional<Course> course = courseService.findCourseById(courseId);
            course.ifPresent(value -> response.setData(courseContentService.getCourseContents(value.getId())));
        } else {
            throw new AccessDeniedException("403:: forbidden");
        }
        response.setSuccess(response.getData() != null);
        return response;
    }

    @GetMapping(path = "/courses/{courseId}/contents/{fileName}")
    @Operation(summary = "this api is to download a course content file")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "success")})
    public void downloadSingleContent(@PathVariable("courseId") Long courseId, @PathVariable("fileName") String fileName,
                                      @RequestHeader("AUTHORIZATION") String header, HttpServletResponse response) {

        if (isDownloadAllowed(header, courseId)) {
            InputStream inputStream = null;
            try {
                Optional<Course> course = courseService.findCourseById(courseId);
                if (course.isPresent()) {
                    inputStream = minioService.get(Path.of(course.get().getCourseName() + "/" + fileName));
                    // Set the content type and attachment header.
                    response.addHeader("Content-disposition", "attachment;filename=" + fileName);
                    response.setContentType(URLConnection.guessContentTypeFromName(fileName));
                    // Copy the stream to the response's output stream.
                    IOUtils.copy(inputStream, response.getOutputStream());
                    response.flushBuffer();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new AccessDeniedException("403 :: forbidden");
        }
    }

    private boolean isDownloadAllowed(String header, Long courseId) {

        Optional<Teacher> teacher = null;
        Optional<Student> student = null;
        Optional<Admin> admin = null;
        boolean allowed = false;
        Long userId = getUserIdFromJwt(header);
        String role = getUserRoleFromJwt(header);
        if (role.equals("TEACHER")) {
            teacher = teacherService.findTeacherById(userId);
        } else if (role.equals("STUDENT")) {
            student = studentService.findStudentById(getUserIdFromJwt(header));
        } else if (role.equals("ADMIN")) {
            admin = adminService.findAdminById(getUserIdFromJwt(header));
        }
        Optional<Course> course = courseService.findCourseById(courseId);
        if (course.isPresent()) {
            if (student != null && student.isPresent()) {
                if (student.get().getCourses().contains(course.get())) {
                    allowed = true;
                }
            } else if (teacher != null && teacher.isPresent()) {
                if (teacher.get().getCourses().contains(course.get())) {
                    allowed = true;
                }
            } else if (admin != null && admin.isPresent()) {
                allowed = true;
            }
        }
        return allowed;
    }

    private boolean isUploadAllowed(String header, Long courseId) {

        boolean allowed = false;
        Optional<Teacher> user = teacherService.findTeacherById(getUserIdFromJwt(header));
        Optional<Course> course = courseService.findCourseById(courseId);
        if (course.isPresent()) {
            if (user.isPresent() && user.get().getRole().equals("student")) {
                return false;
            } else if (user.isPresent() && user.get().getRole().equals("teacher")) {
                Teacher teacher = teacherService.findTeacherByEmail(user.get().getEmail());
                if (teacher.getCourses().contains(course.get())) {
                    allowed = true;
                }
            } else if (user.get().getRole().equals("admin")) {
                allowed = true;
            }
        }
        return allowed;
    }
}
