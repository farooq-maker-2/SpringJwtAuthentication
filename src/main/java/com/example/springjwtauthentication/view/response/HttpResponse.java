package com.example.springjwtauthentication.view.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class HttpResponse<T> {

        private boolean success;
        private int httpCode;
        private String message;
        private T data;
}
