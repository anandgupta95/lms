//package com.lms.exception;
//
//import com.lms.exception.auth.*;
//import com.lms.exception.user.*;
//import com.lms.exception.course.*;
//import com.lms.exception.enrollment.*;
//import com.lms.exception.system.*;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//
//import com.lms.dto.ApiResponse;
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    // Helper method to build API responses
//    private <T> ResponseEntity<ApiResponse<T>> buildResponse(HttpStatus status, String message, T data) {
//        ApiResponse<T> response = new ApiResponse<>("error", message, data);
//        return new ResponseEntity<>(response, status);
//    }
//
//    // ===================== Auth Exceptions =====================
//    @ExceptionHandler(UnauthorizedException.class)
//    public ResponseEntity<ApiResponse<String>> handleUnauthorized(UnauthorizedException ex) {
//        return buildResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), null);
//    }
//
//    @ExceptionHandler(ForbiddenException.class)
//    public ResponseEntity<ApiResponse<String>> handleForbidden(ForbiddenException ex) {
//        return buildResponse(HttpStatus.FORBIDDEN, ex.getMessage(), null);
//    }
//
//    @ExceptionHandler(TokenExpiredException.class)
//    public ResponseEntity<ApiResponse<String>> handleTokenExpired(TokenExpiredException ex) {
//        return buildResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), null);
//    }
//
//    // ===================== User Exceptions =====================
//    @ExceptionHandler(UserNotFoundException.class)
//    public ResponseEntity<ApiResponse<String>> handleUserNotFound(UserNotFoundException ex) {
//        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), null);
//    }
//
//    @ExceptionHandler(RoleNotAllowedException.class)
//    public ResponseEntity<ApiResponse<String>> handleRoleNotAllowed(RoleNotAllowedException ex) {
//        return buildResponse(HttpStatus.FORBIDDEN, ex.getMessage(), null);
//    }
//
//    @ExceptionHandler(InvalidCredentialsException.class)
//    public ResponseEntity<ApiResponse<String>> handleInvalidCredentials(InvalidCredentialsException ex) {
//        return buildResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), null);
//    }
//
//    @ExceptionHandler(UserAlreadyExistsException.class)
//    public ResponseEntity<ApiResponse<String>> handleUserExists(UserAlreadyExistsException ex) {
//        return buildResponse(HttpStatus.CONFLICT, ex.getMessage(), null);
//    }
//
//    // ===================== Course Exceptions =====================
//    @ExceptionHandler(CourseNotFoundException.class)
//    public ResponseEntity<ApiResponse<String>> handleCourseNotFound(CourseNotFoundException ex) {
//        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), null);
//    }
//
//    @ExceptionHandler(DuplicateCourseException.class)
//    public ResponseEntity<ApiResponse<String>> handleDuplicateCourse(DuplicateCourseException ex) {
//        return buildResponse(HttpStatus.CONFLICT, ex.getMessage(), null);
//    }
//
//    @ExceptionHandler(ModuleNotFoundException.class)
//    public ResponseEntity<ApiResponse<String>> handleModuleNotFound(ModuleNotFoundException ex) {
//        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), null);
//    }
//
//    @ExceptionHandler(AssignmentNotFoundException.class)
//    public ResponseEntity<ApiResponse<String>> handleAssignmentNotFound(AssignmentNotFoundException ex) {
//        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), null);
//    }
//
//    // ===================== Enrollment Exceptions =====================
//    @ExceptionHandler(AlreadyEnrolledException.class)
//    public ResponseEntity<ApiResponse<String>> handleAlreadyEnrolled(AlreadyEnrolledException ex) {
//        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), null);
//    }
//
//    @ExceptionHandler(NotEnrolledException.class)
//    public ResponseEntity<ApiResponse<String>> handleNotEnrolled(NotEnrolledException ex) {
//        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), null);
//    }
//
//    @ExceptionHandler(SubmissionNotAllowedException.class)
//    public ResponseEntity<ApiResponse<String>> handleSubmissionNotAllowed(SubmissionNotAllowedException ex) {
//        return buildResponse(HttpStatus.FORBIDDEN, ex.getMessage(), null);
//    }
//
//    @ExceptionHandler(SubmissionNotFoundException.class)
//    public ResponseEntity<ApiResponse<String>> handleSubmissionNotFound(SubmissionNotFoundException ex) {
//        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), null);
//    }
//
//    // ===================== System Exceptions =====================
//    @ExceptionHandler(InvalidInputException.class)
//    public ResponseEntity<ApiResponse<String>> handleInvalidInput(InvalidInputException ex) {
//        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), null);
//    }
//
//    @ExceptionHandler(ResourceConflictException.class)
//    public ResponseEntity<ApiResponse<String>> handleResourceConflict(ResourceConflictException ex) {
//        return buildResponse(HttpStatus.CONFLICT, ex.getMessage(), null);
//    }
//
//    @ExceptionHandler(DeadlineExceededException.class)
//    public ResponseEntity<ApiResponse<String>> handleDeadlineExceeded(DeadlineExceededException ex) {
//        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), null);
//    }
//
//    @ExceptionHandler(FileUploadException.class)
//    public ResponseEntity<ApiResponse<String>> handleFileUpload(FileUploadException ex) {
//        return buildResponse(HttpStatus.CONFLICT, ex.getMessage(), null);
//    }
//
//    @ExceptionHandler(InternalServerErrorException.class)
//    public ResponseEntity<ApiResponse<String>> handleInternalServer(InternalServerErrorException ex) {
//        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
//    }
//
//    @ExceptionHandler(DatabaseException.class)
//    public ResponseEntity<ApiResponse<String>> handleDatabase(DatabaseException ex) {
//        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
//    }
//
//    @ExceptionHandler(ServiceUnavailableException.class)
//    public ResponseEntity<ApiResponse<String>> handleServiceUnavailable(ServiceUnavailableException ex) {
//        return buildResponse(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage(), null);
//    }
//
//    // ===================== Fallback Exception =====================
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiResponse<String>> handleGeneric(Exception ex) {
//        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error: " + ex.getMessage(), null);
//    }
//}
