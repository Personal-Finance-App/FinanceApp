package financeapp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@ControllerAdvice
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class ErrorHandler {
    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<AppError> catchAllException(Exception e) {
        log.error(e.getLocalizedMessage());
        log.error(Arrays.toString(e.getStackTrace()));
        return ResponseEntity
                .internalServerError()
                .contentType(MediaType.APPLICATION_JSON)
                .body(AppError.builder().error(e.getLocalizedMessage()).build());
    }

}
