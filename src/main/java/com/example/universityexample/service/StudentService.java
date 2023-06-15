package com.example.universityexample.service;

import com.example.universityexample.entity.Student;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface StudentService {
    List<Student> getAllStudents();
    Optional<Student> getStudentById(Integer id);

    int createOrUpdateStudent(Student student);

    int deleteStudentById(int id);


    int createStudent(Student student);

    // other service that maybe useful?
    // int deleteStudent(Student student);

}
