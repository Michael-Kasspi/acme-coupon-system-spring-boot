package com.acme.couponsystem.db.rest.controller.exhandler;

import com.acme.couponsystem.db.rest.SessionService;
import com.acme.couponsystem.db.rest.controller.ex.InvalidTokenException;
import com.acme.couponsystem.db.rest.controller.ex.SessionExpiredException;
import com.acme.couponsystem.service.ex.*;
import com.acme.couponsystem.service.storage.ex.FileNotFoundException;
import com.acme.couponsystem.service.storage.ex.FileStorageException;
import org.hibernate.search.exception.SearchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private SessionService sessionService;

    @Autowired
    public GlobalExceptionHandler(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @ExceptionHandler(NoSuchResourceException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchResourceException(RuntimeException ex) {
        return handleErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnableToUpdateResourceException.class)
    public ResponseEntity<ErrorResponse> handleUnableToUpdateResourceException(RuntimeException ex) {
        return handleErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = DuplicateCouponPurchaseException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateCouponPurchaseException(RuntimeException ex) {
        return handleErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = InsufficientCreditAmountException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientCreditAmountException(RuntimeException ex) {
        return handleErrorResponse(ex.getMessage(), HttpStatus.PAYMENT_REQUIRED);
    }

    @ExceptionHandler(value = ForbiddenActionException.class)
    public ResponseEntity<ErrorResponse> handleForbiddenActionException(RuntimeException ex) {
        return handleErrorResponse(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {
            InvalidLoginCredentialsException.class,
            InvalidTokenException.class,
            SessionExpiredException.class,
            TerminatedAccountException.class
    })
    public ResponseEntity<ErrorResponse> handleUnauthorizedAccessException(
            RuntimeException ex, HttpServletResponse response) {
        // clear session from the unauthorized user
        sessionService.clearSessionCookies(response);

        return handleErrorResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = UnableToUpdateAccountCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleUnableToUpdateAccountCredentialsException(RuntimeException ex) {
        return handleErrorResponse(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = UnsupportedUserTypeException.class)
    public ResponseEntity<ErrorResponse> handleUnsupportedUserTypeException(RuntimeException ex) {
        return handleErrorResponse(ex.getMessage(), HttpStatus.NOT_IMPLEMENTED);
    }

    @ExceptionHandler(value = CouponNotInStockException.class)
    public ResponseEntity<ErrorResponse> handleCouponNotInStockException(RuntimeException ex) {
        return handleErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationExceptions(RuntimeException ex) {
        return handleErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = SearchException.class)
    public ResponseEntity<ErrorResponse> handleSearchException(RuntimeException ex) {
        return handleErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MalformedAccountException.class)
    public ResponseEntity<ErrorResponse> handleMalformedAccountException(RuntimeException ex) {
        return handleErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MalformedCouponException.class)
    public ResponseEntity<ErrorResponse> handleMalformedCouponException(RuntimeException ex) {
        return handleErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UnableToRemoveCouponException.class)
    public ResponseEntity<ErrorResponse> handleUnableToRemoveCouponException(
            RuntimeException ex, WebRequest request) {
        return handleErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = DuplicateEmailException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateEmailException(RuntimeException ex) {
        return handleErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = AccountWithoutUserException.class)
    public ResponseEntity<ErrorResponse> handleAccountWithoutUserException(RuntimeException ex) {
        return handleErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = FileNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleFileNotFoundException(RuntimeException ex) {
        return handleErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = FileStorageException.class)
    public ResponseEntity<ErrorResponse> handleFileStorageException(RuntimeException ex) {
        return handleErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse> handleMaxUploadSizeExceededException(RuntimeException ex) {
        String message = "The maximum file size has been exceeded";
        return handleErrorResponse(message, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponse> handleErrorResponse(String message, HttpStatus status) {
        return new ResponseEntity<>(new ErrorResponse(message, status.getReasonPhrase()), status);
    }

    private static class ErrorResponse{

        private String message;

        private String statusText;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public ErrorResponse(String message, String statusText) {
            this.message = message;
            this.statusText = statusText;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getStatusText() {
            return statusText;
        }

        public void setStatusText(String statusText) {
            this.statusText = statusText;
        }
    }

}
