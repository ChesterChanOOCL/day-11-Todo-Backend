

import com.example.demo.Exception.TodoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TodoNotFoundException.class)
    @ResponseStatus( HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleEmployeeNotFoundException(TodoNotFoundException exception) {
        return new ErrorResponse(exception.getMessage()) {

        };
    }
}
