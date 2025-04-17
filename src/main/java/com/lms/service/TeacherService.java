package com.lms.service;

import com.lms.model.Teacher;
import com.lms.model.Auth;
import com.lms.repository.TeacherRepository;
import com.lms.repository.AuthRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    TeacherRepository teacherRepository;
    AuthRepository AuthRepository;

    public TeacherService(TeacherRepository teacherRepository, AuthRepository AuthRepository){
        this.teacherRepository = teacherRepository;
        this.AuthRepository = AuthRepository;
    }

    public Teacher createTeacher(long AuthId, Teacher teacher) throws BadRequestException {
        Auth Authteacher =AuthRepository.findById(AuthId).orElseThrow(() -> new EntityNotFoundException("No Auth found with this id : "+ AuthId));

        if(!("TEACHER".equals(Authteacher.getRole().name()))){
            throw new BadRequestException("Auth has not permission for teacher as role defined as ."+Authteacher.getRole()+".");
        }

        else{
            System.out.println("i am here ");
            return teacherRepository.save(teacher);
        }

    }

    public List<Teacher> getTeacher(){
        return teacherRepository.findAll();
    }

    public Teacher getTeacher(long id){
        return teacherRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Teacher is not exist with this : "+id));
    }

    public Teacher updateTeacher(long id, Teacher teacherDetails){
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No Teacher Found using given id : "+id));
//        teacher.setName(teacherDetails.getName());
        return teacherRepository.save(teacher);
    }

    public void deleteTeacher(long id){
        teacherRepository.deleteById(id);
    }

}
