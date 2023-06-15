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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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



    @GetMapping("/studentsof/{id}")
    public ResponseEntity<?> getStudentsByTeacherId(@PathVariable Integer id){
        //List<TeacherStudent> result = teacherStudentService.getTeachersByStudentId(id);

        Optional<Teacher> teacher = teacherService.getTeacherById(id);
        if(teacher.isPresent()){
            List<String> teachers = teacher.get().getTeacherStudents().stream()
                    .map((TeacherStudent a)->(a.getStu().getId() + "." + a.getStu().getName()))
                    .toList();
            return new ResponseEntity<>(teachers ,HttpStatus.OK);
        } else{
            throw new UserNotFoundException();
        }

    }

    @GetMapping("/teachersof/{id}")
    public ResponseEntity<?> getTeachersByStudentId(@PathVariable Integer id){
        //List<TeacherStudent> result = teacherStudentService.getStudentsByTeacherID(id);


        Optional<Student> student = studentService.getStudentById(id);
        if(student.isPresent()){
            List<String> students = student.get().getTeacherStudents().stream()
                    .map((TeacherStudent a)->(a.getTeacher().getId() + "." + a.getTeacher().getName()))
                    .toList();
            return new ResponseEntity<>(students ,HttpStatus.OK);
        } else{
            throw new UserNotFoundException();
        }
    }


}
