package com.example.universityexample.controller;

import com.example.universityexample.entity.Student;
import com.example.universityexample.entity.Teacher;
import com.example.universityexample.entity.TeacherStudent;
import com.example.universityexample.exception.UserNotFoundException;
import com.example.universityexample.service.StudentService;
import com.example.universityexample.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.stream;

@RestController
public class TeacherStudentController {

    private final StudentService studentService;
    private final TeacherService teacherService;

    @Autowired
    public TeacherStudentController(StudentService studentService,TeacherService teacherService){
        this.studentService = studentService;
        this.teacherService = teacherService;
    }



    @RequestMapping(value="/relation",params="teacher",method = RequestMethod.GET)
    public ResponseEntity<?> getStudentsByTeacherId(@RequestParam Integer teacher){
        //List<TeacherStudent> result = teacherStudentService.getTeachersByStudentId(id);

        Optional<Teacher> teacherFound = teacherService.getTeacherById(teacher);
        if(teacherFound.isPresent()){
            List<String> teachers = teacherFound.get().getTeacherStudents().stream()
                    .map((TeacherStudent a)->(a.getStu().getId() + "." + a.getStu().getName()))
                    .toList();
            return new ResponseEntity<>(teachers ,HttpStatus.OK);
        } else{
            throw new UserNotFoundException();
        }

    }


    @RequestMapping(value="/relation",params="student",method = RequestMethod.GET)
    public ResponseEntity<?> getTeachersByStudentId(@RequestParam Integer student){
        //List<TeacherStudent> result = teacherStudentService.getStudentsByTeacherID(id);


        Optional<Student> studentFound = studentService.getStudentById(student);
        if(studentFound.isPresent()){
            List<String> students = studentFound.get().getTeacherStudents().stream()
                    .map((TeacherStudent a)->(a.getTeacher().getId() + "." + a.getTeacher().getName()))
                    .toList();
            return new ResponseEntity<>(students ,HttpStatus.OK);
        } else{
            throw new UserNotFoundException();
        }
    }


}
