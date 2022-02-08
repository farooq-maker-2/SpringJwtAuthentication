package com.example.springjwtauthentication.service;

import com.example.springjwtauthentication.entity.Teacher;
import com.example.springjwtauthentication.entity.User;
import com.example.springjwtauthentication.model.TeacherModel;
import com.example.springjwtauthentication.repository.TeacherRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    public User saveTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

//    public Page<UserView> findAll(PageRequest of) {
//        List<TeacherModel> teacherModels = userRepository.findAByRoleContaining("teacher", of).stream().map(t -> this.toModel(t)).collect(Collectors.toList());
//        List<UserView> userViews = new ArrayList<>();
//        teacherModels.stream().forEach(teacherModel -> {
//            userViews.add(UserView.toTeacherView(teacherModel));
//        });
//        return new PageImpl<>(userViews);
//    }

    private TeacherModel toModel(User teacher) {
        return TeacherModel.builder()
                .id(teacher.getId())
                .email(teacher.getEmail())
                .password(teacher.getPassword())
                .firstName(teacher.getFirstName())
                .lastName(teacher.getLastName())
                .status(teacher.getStatus())
                .build();
    }

    public TeacherModel toModel(Teacher teacher) {

        return TeacherModel.builder()
                .id(teacher.getId())
                .email(teacher.getEmail())
                .password(teacher.getPassword())
                .firstName(teacher.getFirstName())
                .lastName(teacher.getLastName())
                .status(teacher.getStatus())
                .build();
    }

    public User toEntity(User teacher) {
        return User.builder()
                .id(teacher.getId())
                .email(teacher.getEmail())
                .password(teacher.getPassword())
                .firstName(teacher.getFirstName())
                .lastName(teacher.getLastName())
                .status(teacher.getStatus())
                .build();

    }
}
