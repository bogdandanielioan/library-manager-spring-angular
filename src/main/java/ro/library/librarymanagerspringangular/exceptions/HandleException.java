package ro.library.librarymanagerspringangular.exceptions;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ro.library.librarymanagerspringangular.model.HttpResponse;

import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
@Slf4j
public class HandleException extends ResponseEntityExceptionHandler implements ErrorController {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(exception.getMessage());
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));
        return new ResponseEntity<>(
                HttpResponse.builder()
                        .reason("Invalid fields: " + fieldsMessage)
                        .developerMessage(exception.getMessage())
                        .status(status)
                        .statusCode(status.value())
                        .build(), status);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception exception, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(
                HttpResponse.builder()
                        .reason(exception.getMessage())
                        .developerMessage(exception.getMessage())
                        .status(status)
                        .statusCode(status.value())
                        .build(), status);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<HttpResponse<?>> illegalStateException(IllegalStateException exception) {
        return createHttpErrorResponse(BAD_REQUEST, exception.getMessage(), exception);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<HttpResponse<?>> noteNotFoundException(BookNotFoundException exception) {
        return createHttpErrorResponse(BAD_REQUEST, exception.getMessage(), exception);
    }

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<HttpResponse<?>> noResultException(NoResultException exception) {
        return createHttpErrorResponse(INTERNAL_SERVER_ERROR, exception.getMessage(), exception);
    }

    @ExceptionHandler(ServletException.class)
    public ResponseEntity<HttpResponse<?>> servletException(ServletException exception) {
        return createHttpErrorResponse(INTERNAL_SERVER_ERROR, exception.getMessage(), exception);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponse<?>> exception(Exception exception) {
        return createHttpErrorResponse(INTERNAL_SERVER_ERROR, exception.getMessage(), exception);
    }

    private ResponseEntity<HttpResponse<?>> createHttpErrorResponse(HttpStatus httpStatus, String reason, Exception exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(
                HttpResponse.builder()
                        .reason(reason)
                        .developerMessage(exception.getMessage())
                        .status(httpStatus)
                        .statusCode(httpStatus.value())
                        .build(), httpStatus);
    }
}
