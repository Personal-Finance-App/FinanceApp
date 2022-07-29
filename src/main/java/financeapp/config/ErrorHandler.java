package financeapp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

@ControllerAdvice
public class ErrorHandler {
    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<AppError> catchAllException(Exception e) {
        Logger logger = LoggerFactory.getLogger(ErrorHandler.class);
        logger.error(e.getLocalizedMessage());
        logger.error(Arrays.toString(e.getStackTrace()));
        return ResponseEntity
                .internalServerError()
                .contentType(MediaType.APPLICATION_JSON)
                .body(AppError.builder().error(e.getLocalizedMessage()).build());

    }

    ;

}
