package com.example.springjwtauthentication.view;

import com.example.springjwtauthentication.model.StudentModel;
import com.example.springjwtauthentication.model.TeacherModel;
import com.example.springjwtauthentication.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserView {

    private Long id;
    private String firstName;
    private String lastName;
    private String status = "activated";
    private String role;

    public static UserView toStudentView(StudentModel studentModel) {

        return UserView.builder().id(studentModel.getId())
                .firstName(studentModel.getFirstName())
                .lastName(studentModel.getLastName())
                .status(studentModel.getStatus())
                .role(studentModel.getRole()).build();

    }

    public static UserView toTeacherView(TeacherModel teacherModel) {

        return UserView.builder().id(teacherModel.getId())
                .firstName(teacherModel.getFirstName())
                .lastName(teacherModel.getLastName())
                .status(teacherModel.getStatus())
                .role(teacherModel.getRole()).build();

    }

    public static UserView toUserView(UserModel userModel) {
        return UserView.builder().id(userModel.getId())
                .firstName(userModel.getFirstName())
                .lastName(userModel.getLastName())
                .status(userModel.getStatus())
                .role(userModel.getRole()).build();
    }
}
