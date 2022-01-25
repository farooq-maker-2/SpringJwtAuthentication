package com.example.springjwtauthentication.service;

import com.example.springjwtauthentication.entity.Teacher;
import com.example.springjwtauthentication.model.CourseModel;
import com.example.springjwtauthentication.model.StudentModel;
import com.example.springjwtauthentication.model.TeacherModel;
import com.example.springjwtauthentication.model.UserModel;
import com.example.springjwtauthentication.repository.TeacherRepository;
import com.example.springjwtauthentication.entity.User;
import com.example.springjwtauthentication.repository.UserRepository;
import com.example.springjwtauthentication.view.UserView;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Log4j2
public class TeacherService/* implements UserDetailsService*/ {

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private UserRepository userRepository;

    public Teacher saveTeacher(TeacherModel teacher) {
        return teacherRepository.save(this.toEntity(teacher));
    }

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Teacher teacher = teacherRepository.findTeacherByEmail(email);
//        if (teacher == null) {
//            log.error("Teacher not found in database");
//            throw new UsernameNotFoundException("Invalid Username or Password");
//        } else {
//            log.info("user found in the database: {}", email);
//        }
//
//        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        teacher.getRoles().forEach(role -> {
//            authorities.add(new SimpleGrantedAuthority(role.getRole()));
//        });
//
//        return new org.springframework.security.core.userdetails.User(teacher.getEmail(), teacher.getPassword(), authorities);
//    }

//    public Teacher findTeacherByEmail(String email) {
//        return teacherRepository.findTeacherByEmail(email);
//    }

    public Teacher findTeacherById(Long teacherId) {
        return teacherRepository.findTeacherById(teacherId);
    }

    public Page<UserView> findAll(PageRequest of) {
        List<TeacherModel> teacherModels = userRepository.findAByRoleContaining("teacher", of).stream().map(t -> this.toModel(t)).collect(Collectors.toList());
        List<UserView> userViews = new ArrayList<>();
        teacherModels.stream().forEach(teacherModel -> {
            userViews.add(UserView.toTeacherView(teacherModel));
        });
        return new PageImpl<>(userViews);
    }

    private TeacherModel toModel(User teacher) {
        return TeacherModel.builder()
                .id(teacher.getId())
                .email(teacher.getEmail())
                .password(teacher.getPassword())
                .firstName(teacher.getFirstName())
                .lastName(teacher.getLastName())
                .status(teacher.getStatus())
//                .courses(coursesOfTeacher)
                .build();
    }

    public List<TeacherModel> findTeachersByName(PageRequest pageRequest, String name) {
        List<User> teachersByLastName = userRepository.findTeachersByLastNameAndRole(/*pageRequest,*/ name, "teacher");
        List<User> teachersByFirstName = userRepository.findTeachersByFirstNameAndRole(/*pageRequest,*/ name, "teacher");
        return Stream.concat(teachersByLastName.stream(), teachersByFirstName.stream())
                .collect(Collectors.toList()).stream().map(teacher -> this.toModel(teacher)).collect(Collectors.toList());
    }

    public TeacherModel findTeacherByEmail(String email) {
        Teacher teacher = teacherRepository.findTeacherByEmail(email);
        return this.toModel(teacher);
    }

    public TeacherModel toModel(Teacher teacher) {

//        Set<CourseModel> coursesOfTeacher = (teacher.getCourses() != null) ?
//                teacher.getCourses().stream().map(course -> courseService.toModel(course)).collect(Collectors.toSet()) :
//                new HashSet<>();

        return TeacherModel.builder()
                .id(teacher.getId())
                .email(teacher.getEmail())
                .password(teacher.getPassword())
                .firstName(teacher.getFirstName())
                .lastName(teacher.getLastName())
                .status(teacher.getStatus())
//                .courses(coursesOfTeacher)
                .build();
    }

    public Teacher toEntity(TeacherModel teacher) {
        return Teacher.builder()
                .id(teacher.getId())
                .email(teacher.getEmail())
                .password(teacher.getPassword())
                .firstName(teacher.getFirstName())
                .lastName(teacher.getLastName())
                .status(teacher.getStatus())
                .build();

    }

    public boolean containsCourse(Long id, Long courseId) {
        Teacher teacher = teacherRepository.findTeacherById(id);
        AtomicBoolean result = new AtomicBoolean(false);
        teacher.getCourses().stream().forEach(c -> {
            if (c.getId() == courseId) {
                result.set(true);
            }
        });
        return result.get();
    }
}
