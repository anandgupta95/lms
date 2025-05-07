package com.lms.exception.enrollment;

public class SubmissionNotAllowedException extends RuntimeException {
    public SubmissionNotAllowedException(String message) {
        super(message);
    }
}
