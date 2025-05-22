package dasturlash.uz.controllers;

import dasturlash.uz.exp.AppBadExp;
import dasturlash.uz.exp.NotFoundExp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({AppBadExp.class,NotFoundExp.class})
    public ResponseEntity<String> handleAppBadExp(AppBadExp e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }


}
