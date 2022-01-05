//package com.example.springjwtauthentication.helpers;
//
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//import javax.persistence.EntityNotFoundException;
//
//import static ch.qos.logback.core.util.AggregationType.NOT_FOUND;
//
//@Order(Ordered.HIGHEST_PRECEDENCE)
//@ControllerAdvice
//public class RestExceptionHandler extends ResponseEntityExceptionHandler {
//
//    //other exception handlers
//
//    @ExceptionHandler(EntityNotFoundException.class)
//    protected ResponseEntity<Object> handleEntityNotFound(
//            EntityNotFoundException ex) {
//        ApiError apiError = new ApiError(NOT_FOUND);
//        apiError.setMessage(ex.getMessage());
//        return buildResponseEntity(apiError);
//    }
//}