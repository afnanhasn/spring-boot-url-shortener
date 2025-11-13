package com.realprojects.urlshortener.web;

import com.realprojects.urlshortener.domain.exceptions.ShortUrlNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


/* Creating a global handler class with controller advice is one of the many ways to handle exceptions.
If controllers come across any error or exception which isn't handled then this can take care of it.*/
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ShortUrlNotFoundException.class)
    String handleShortUrlNotFoundException(ShortUrlNotFoundException ex) {
        log.error("Short URL not found: {}", ex.getMessage());
        return "error/404"; //thymeleaf based html error pages
    }

    @ExceptionHandler(Exception.class)
    String handleException(Exception ex) {
        log.error("Unhandled exception: {}", ex.getMessage(), ex);
        return "error/500";
    }
}
