//package com.lms.model;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//
//@Entity
//@Table(name="roles")
//
//public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;

//    @NonNull
//    @Enumerated(EnumType.STRING)
//    private Role role = Role.STUDENT;
//
//    public enum Role{
//        STUDENT, TEACHER, ADMIN
//    }
//
//    @OneToOne
//    @JoinColumn(name = "auth_id")
//    private Auth auth;
//
//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
//    private Student student;
//
//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
//    private Teacher teacher;
//
//    public Student getStudent() {
//        return student;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public Auth getAuth() {
//        return auth;
//    }
//
//    public void setAuth(Auth auth) {
//        this.auth = auth;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public void setStudent(Student student) {
//        this.student = student;
//    }
//
//    public Teacher getTeacher() {
//        return teacher;
//    }
//
//    public void setTeacher(Teacher teacher) {
//        this.teacher = teacher;
//    }
//
//
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//
//
//    public @NonNull Role getRole() {
//        return role;
//    }
//
//    public void setRole(@NonNull Role role) {
//        this.role = role;
//    }
//}
