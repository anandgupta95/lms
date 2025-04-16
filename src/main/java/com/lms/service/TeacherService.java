package com.lms.service;

import com.lms.model.Teacher;
import com.lms.model.User;
import com.lms.repository.TeacherRepository;
import com.lms.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    TeacherRepository teacherRepository;
    UserRepository userRepository;

    public TeacherService(TeacherRepository teacherRepository, UserRepository userRepository){
        this.teacherRepository = teacherRepository;
        this.userRepository = userRepository;
    }

    public Teacher createTeacher(long userId, Teacher teacher) throws BadRequestException {
        User userteacher =userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("No User found with this id : "+ userId));

        if(!("TEACHER".equals(userteacher.getRole().name()))){
            throw new BadRequestException("user has not permission for teacher as role defined as ."+userteacher.getRole()+".");
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
