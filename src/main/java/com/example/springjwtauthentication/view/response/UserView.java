package com.example.springjwtauthentication.view.response;

import com.example.springjwtauthentication.entity.Admin;
import com.example.springjwtauthentication.entity.Student;
import com.example.springjwtauthentication.entity.Teacher;
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

    public static UserView toUserView(Student studentModel) {

        if (studentModel == null) {
            return null;
        } else {
            return UserView.builder().id(studentModel.getId())
                    .firstName(studentModel.getFirstName())
                    .lastName(studentModel.getLastName())
                    .status(studentModel.getStatus())
                    .role(studentModel.getRole()).build();
        }
    }

    public static UserView toUserView(Teacher teacherModel) {

        if (teacherModel == null) {
            return null;
        } else {
            return UserView.builder().id(teacherModel.getId())
                    .firstName(teacherModel.getFirstName())
                    .lastName(teacherModel.getLastName())
                    .status(teacherModel.getStatus())
                    .role(teacherModel.getRole()).build();
        }
    }

    public static UserView toUserView(UserModel userModel) {
        if (userModel == null) {
            return null;
        } else {
            return UserView.builder().id(userModel.getId())
                    .firstName(userModel.getFirstName())
                    .lastName(userModel.getLastName())
                    .status(userModel.getStatus())
                    .role(userModel.getRole()).build();
        }
    }

    public static UserView toUserView(Admin adminModel) {
        if (adminModel == null) {
            return null;
        } else {
            return UserView.builder().id(adminModel.getId())
                    .firstName(adminModel.getFirstName())
                    .lastName(adminModel.getLastName())
                    .status(adminModel.getStatus())
                    .role(adminModel.getRole()).build();
        }
    }
}
