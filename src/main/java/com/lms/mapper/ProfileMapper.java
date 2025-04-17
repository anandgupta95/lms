package com.lms.mapper;

import com.lms.dto.profile.UpdateProfile;
import com.lms.model.Auth;
import com.lms.model.Student;
import com.lms.model.Teacher;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {

   public void toAuthEntity(UpdateProfile updateProfile, Auth auth){

      auth.setUsername(updateProfile.getUsername());
      auth.setEmail(updateProfile.getEmail());

   }
   public void toStudentEntity(UpdateProfile updateProfile, Student student){
      student.setAddress(updateProfile.getAddress());
      student.setFullName(updateProfile.getFullName());
      student.setPhoneNumber(updateProfile.getPhoneNumber());
   }
   public void toTeacherEntity(UpdateProfile updateProfile, Teacher teacher){
      teacher.setAddress(updateProfile.getAddress());
      teacher.setFullName(updateProfile.getFullName());
      teacher.setPhoneNumber(updateProfile.getPhoneNumber());

   }

   public UpdateProfile toDto(Auth user, Student student){
      UpdateProfile updateProfile = new UpdateProfile();

      updateProfile.setUsername(user.getUsername());
      updateProfile.setEmail(user.getEmail());

      updateProfile.setAddress(student.getAddress());
      updateProfile.setFullName(student.getFullName());
      updateProfile.setPhoneNumber(student.getPhoneNumber());

      return updateProfile;
   }
   public UpdateProfile toDto(Auth user, Teacher teacher){
      UpdateProfile updateProfile = new UpdateProfile();


      updateProfile.setUsername(user.getUsername());
      updateProfile.setEmail(user.getEmail());

      updateProfile.setAddress(teacher.getAddress());
      updateProfile.setFullName(teacher.getFullName());
      updateProfile.setPhoneNumber(teacher.getPhoneNumber());

      return updateProfile;
   }
}
