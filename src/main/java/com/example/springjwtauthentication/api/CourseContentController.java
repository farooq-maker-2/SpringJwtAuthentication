package com.example.springjwtauthentication.api;

import com.example.springjwtauthentication.content.Content;
import com.example.springjwtauthentication.course.Course;
import com.example.springjwtauthentication.service.CourseContentService;
import com.example.springjwtauthentication.service.CourseService;
import com.example.springjwtauthentication.service.UserService;
import com.example.springjwtauthentication.teacher.Teacher;
import com.example.springjwtauthentication.service.TeacherService;
import com.example.springjwtauthentication.user.User;
import com.google.api.client.util.IOUtils;
import com.jlefebure.spring.boot.minio.MinioException;
import com.jlefebure.spring.boot.minio.MinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class CourseContentController {

    @Autowired
    private MinioService minioService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseContentService courseContentService;

    @PostMapping(value = "/teachers/{teacherId}/courses/{courseId}/contents"/*,
            consumes = {"multipart/mixed", "multipart/form-data"}*/)
    public String uploadCourseContent(@RequestHeader("AUTHORIZATION") String header,
                                      @PathVariable("teacherId") Long teacherId,
                                      @PathVariable("courseId") Long courseId,
//                                       @RequestBody MultipartFile file
                                      //@RequestParam Map<String, String> file
                                      @RequestParam("file") MultipartFile file
                                      //@RequestPart(value = "file") MultipartFile file
            /*@RequestBody MultipartFile file*/) {

        User user = userService.findUserById(teacherId);
        Teacher teacher = teacherService.findTeacherByEmail(user.getEmail());

        Course course = courseService.findCourseById(courseId);
        if (teacher.getCourses().contains(course)) {
            Content content = Content
                    .builder()
                    .fileName(file.getOriginalFilename())
                    .contentType(file.getContentType())
                    .description(String.valueOf(file.getSize()))
                    .build();

            Path path = Path.of(course.getCourseName() + "/" + file.getOriginalFilename());
            try {
                minioService.upload(path, file.getInputStream(), file.getContentType());
                course.getCourseContents().add(content);
                courseContentService.saveCourseContent(content);
                courseService.saveCourse(course);
                return "success";
            } catch (MinioException e) {
                throw new IllegalStateException("The file cannot be upload on the internal storage. Please retry later", e);
            } catch (IOException e) {
                throw new IllegalStateException("The file cannot be read", e);
            }

            /**
             * Save File conetnt to db
             *
             * */
//            Content content = new Content();
//            content.setFileName(file.getOriginalFilename());
//            content.setDescription(String.valueOf(file.getSize()));
//            content.setContentType(file.getContentType());
//            try {
//                content.setContent(file.getBytes());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            course.getCourseContents().add(content);
//            courseContentService.saveCourseContent(content);
//            courseService.saveCourse(course);
//            return content;


        } else {
            return "upload filed";
        }
    }

    @GetMapping("/courses/{courseId}/contents")
    public List<Content> getAllCourseContentsList(@PathVariable("courseId") Long courseId, @RequestHeader("AUTHORIZATION") String header) {

        Course course = courseService.findCourseById(courseId);
        return course.getCourseContents();
    }


    @GetMapping("/courses/{courseId}/contents/{fileName}")
    public /*ResponseEntity<Resource>*/void downloadSingleContent(@PathVariable("courseId") Long courseId,
                                                                  @PathVariable("fileName") String fileName,
                                                                  @RequestHeader("AUTHORIZATION") String header,
                                                                  HttpServletResponse response) {

        Course course = courseService.findCourseById(courseId);
        InputStream inputStream = null;
        try {
            inputStream = minioService.get(Path.of(course.getCourseName() + "/" + fileName));
            InputStreamResource inputStreamResource = new InputStreamResource(inputStream);

            // Set the content type and attachment header.
            response.addHeader("Content-disposition", "attachment;filename=" + fileName);
            response.setContentType(URLConnection.guessContentTypeFromName(fileName));

            // Copy the stream to the response's output stream.
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        }
//
//        Content content = courseContentService.getSingleContent(contentId);
//
//        return ResponseEntity.ok().
//                contentType(MediaType.parseMediaType(content.getContentType())).
//                header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = " + content.getFileName()).
//                body(new ByteArrayResource(content.getContent()));
    }


}
