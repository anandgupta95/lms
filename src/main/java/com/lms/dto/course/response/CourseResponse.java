package com.lms.dto.course.response;

import com.lms.model.Teacher;
import jakarta.validation.constraints.NotBlank;

public class CourseResponse {
        private Long id;

        @NotBlank(message = "title is needed")
        private String title;

        private String description;
        @NotBlank(message = "url is needed")
        private String url;
        //    @Column(nullable = false)
        private double price;
        //    @Column(nullable = false)
        private String subject;
        //    @Column(nullable = false)
        private String batch;

        private String taughtBy;

        private Long noOfEnrolled;

        private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CourseResponse(){
            this.id = id;
            this.title = title;
            this.description = description;
            this.url = url;
            this.price = price;
            this.subject = subject;
            this.batch = batch;
            this.taughtBy = taughtBy;
            this.noOfEnrolled = noOfEnrolled;
        }

        public Long getNoOfEnrolled() {
            return noOfEnrolled;
        }

        public void setNoOfEnrolled(Long noOfEnrolled) {
            this.noOfEnrolled = noOfEnrolled;
        }

        public String getTaughtBy() {
            return taughtBy;
        }

        public void setTaughtBy(String taughtBy) {
            this.taughtBy = taughtBy;
        }

        public String getBatch() {
            return batch;
        }

        public void setBatch(String batch) {
            this.batch = batch;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public @NotBlank(message = "url is needed") String getUrl() {
            return url;
        }

        public void setUrl(@NotBlank(message = "url is needed") String url) {
            this.url = url;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public @NotBlank(message = "title is needed") String getTitle() {
            return title;
        }

        public void setTitle(@NotBlank(message = "title is needed") String title) {
            this.title = title;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

}
