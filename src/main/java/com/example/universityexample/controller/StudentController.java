package com.example.universityexample.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.universityexample.entity.Student;
import com.example.universityexample.exception.UserNotFoundException;
import com.example.universityexample.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public ResponseEntity<?> getAllStudents() {
        return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.OK);
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Integer id){
        Optional<Student> student = studentService.getStudentById(id);
        if(student.isPresent()){
            return new ResponseEntity<>(student.get(), HttpStatus.OK);
        } else{
            throw new UserNotFoundException();
        }

        // cannot find, return error message
        //return new ResponseEntity<>("cannot find this student", HttpStatus.NOT_FOUND);
    }


    @PutMapping ("/student/{name}")
    public ResponseEntity<?> createOrUpdateStudent(@PathVariable String name){
        Student student = new Student();
        student.setName(name);
        studentService.createOrUpdateStudent(student);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PostMapping ("/student/{name}")
    public ResponseEntity<?> createStudent(@PathVariable String name){
        Student student = new Student();
        student.setName(name);
        int exitCode = studentService.createStudent(student);
        if(exitCode==0){
            return new ResponseEntity<>("success created", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("user exists", HttpStatus.OK);
    }

    @DeleteMapping("/student/{id}")
    public ResponseEntity<?> deleteStudentById(@PathVariable Integer id){
        int exitCode = studentService.deleteStudentById(id);
        if(exitCode==0){
            return new ResponseEntity<>("DELETE SUCCESS", HttpStatus.OK);
        } else{
            throw new UserNotFoundException();
        }
    }
}
