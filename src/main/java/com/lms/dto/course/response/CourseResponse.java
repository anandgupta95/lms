package com.lms.dto.course.response;

public class CourseResponse {

    private Long id;

    private String title;

    private String description;

    private String url;

    private double price;

    private String subject;

    private String batch;

    private String taughtBy;

    private Long noOfEnrolled;

    private String message;

    // Default constructor
    public CourseResponse() {
    }

    // All-args constructor (optional)
    public CourseResponse(Long id, String title, String description, String url, double price,
                          String subject, String batch, String taughtBy, Long noOfEnrolled, String message) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.url = url;
        this.price = price;
        this.subject = subject;
        this.batch = batch;
        this.taughtBy = taughtBy;
        this.noOfEnrolled = noOfEnrolled;
        this.message = message;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getTaughtBy() {
        return taughtBy;
    }

    public void setTaughtBy(String taughtBy) {
        this.taughtBy = taughtBy;
    }

    public Long getNoOfEnrolled() {
        return noOfEnrolled;
    }

    public void setNoOfEnrolled(Long noOfEnrolled) {
        this.noOfEnrolled = noOfEnrolled;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
