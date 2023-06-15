package com.example.universityexample.serviceImpl;

import com.example.universityexample.entity.Teacher;
import com.example.universityexample.repository.StudentRepository;
import com.example.universityexample.repository.TeacherRepository;
import com.example.universityexample.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService{

    private final TeacherRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(TeacherServiceImpl.class.getName());

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository){
        this.repository = teacherRepository;
    }
    @Override
    public Optional<Teacher> getTeacherById(Integer id){
        logger.info("Get teacher with id" + String.valueOf(id));
        return repository.findById(String.valueOf(id));
    }
}
