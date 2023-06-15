package com.example.universityexample.service;

import com.example.universityexample.entity.Student;
import com.example.universityexample.entity.Teacher;

import java.util.Optional;

public interface TeacherService {
    Optional<Teacher> getTeacherById(Integer id);
}
