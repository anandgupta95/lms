package com.lms.service;

import com.lms.model.EduPoint;
import com.lms.model.Student;
import com.lms.repository.EduPointRepository;
import org.springframework.stereotype.Service;

@Service
public class EduPointService {

    EduPointRepository eduPointRepository;

    public EduPointService(EduPointRepository eduPointRepository){
        this.eduPointRepository = eduPointRepository;
    }

    public void createEduPoint(Student student){
        EduPoint eduPoint = new EduPoint();
        eduPoint.setStudent(student);
        eduPointRepository.save(eduPoint);
    }

    public EduPoint getEduPoint(Long id){
        return eduPointRepository.findById(id).orElseThrow();
    }

    public EduPoint updateEduPoint(Long eduPointId, EduPoint eduPoint){
        EduPoint eduPoint1 = eduPointRepository.findById(eduPointId).orElseThrow();
        eduPoint1.setEduPoints(eduPoint.getEduPoints());
        return eduPointRepository.save(eduPoint1);
    }
}
