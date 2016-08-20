package com.solutionwerk.qb.web.api;

import com.solutionwerk.qb.web.DefaultExceptionAttributes;
import com.solutionwerk.qb.web.ExceptionAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * The BaseController class implements common functionality for all Controller
 * classes. The <code>@ExceptionHandler</code> methods provide a consistent
 * response when Exceptions are thrown from <code>@RequestMapping</code>
 * annotated Controller methods.
 *
 * @author Piyush Ramavat
 */
public class BaseController {

    /**
     * The Logger for this class.
     */
    protected Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /**
     * Handles JPA NoResultExceptions thrown from web service controller
     * methods. Creates a response with Exception Attributes as JSON and HTTP
     * status code 404, not found.
     *
     * @param noResultException A NoResultException instance.
     * @param request           The HttpServletRequest in which the NoResultException was
     *                          raised.
     * @return A ResponseEntity containing the Exception Attributes in the body
     * and HTTP status code 404.
     */
    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<Map<String, Object>> handleNoResultException(NoResultException noResultException, HttpServletRequest request) {

        LOGGER.info("> handleNoResultException");

        ExceptionAttributes exceptionAttributes = new DefaultExceptionAttributes();

        Map<String, Object> responseBody = exceptionAttributes.getExceptionAttributes(noResultException, request, HttpStatus.NOT_FOUND);

        LOGGER.info("< handleNoResultException");
        return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles all Exceptions not addressed by more specific
     * <code>@ExceptionHandler</code> methods. Creates a response with the
     * Exception Attributes in the response body as JSON and a HTTP status code
     * of 500, internal server error.
     *
     * @param exception An Exception instance.
     * @param request   The HttpServletRequest in which the Exception was raised.
     * @return A ResponseEntity containing the Exception Attributes in the body
     * and a HTTP status code 500.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception exception, HttpServletRequest request) {

        LOGGER.error("> handleException");
        LOGGER.error("- Exception: ", exception);

        ExceptionAttributes exceptionAttributes = new DefaultExceptionAttributes();

        Map<String, Object> responseBody = exceptionAttributes.getExceptionAttributes(exception, request, HttpStatus.INTERNAL_SERVER_ERROR);

        LOGGER.error("< handleException");
        return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
