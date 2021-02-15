package moe.ksmz.rodentraid.Response;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    //    @ExceptionHandler(value = {Exception.class})
    //    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
    //        String bodyOfResponse = "something went really wrong";
    //        // todo: finish
    //
    //        return ResponseEntity.ok(bodyOfResponse);
    //    }
}
