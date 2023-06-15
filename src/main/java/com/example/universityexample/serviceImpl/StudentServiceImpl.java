package com.example.universityexample.serviceImpl;

import com.example.universityexample.entity.Student;
import com.example.universityexample.repository.StudentRepository;
import com.example.universityexample.service.StudentService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class.getName());

    @Autowired
     public StudentServiceImpl(StudentRepository repository){
         this.repository = repository;
     }


    @Override
    public List<Student> getAllStudents() {

        logger.info("Get all students...");
        return repository.findAll();
    }

    @Override
    public Optional<Student> getStudentById(Integer id) {
        logger.info("Get students with id" + String.valueOf(id));
         return repository.findById(String.valueOf(id));
    }

    @Override
    public int createOrUpdateStudent(Student student) {
        // here since we only have an auto generated id and a name, it is impossible to know
        // whether the user already exists, so here we assume not allowing two duplicate name
        // but overall this function will do nothing!
        // to do: if we have other identifier like mail, we can first check whether user exists
        // if exists, find out id, update, else create
         try{
             // find duplicate by name
             Optional<Student> studentInDataBase = repository.findOneByName(student.getName());
             if(studentInDataBase.isPresent()){
                 Student newStudent = studentInDataBase.get();
                 newStudent.setName(student.getName());
                 // maybe set other properties here...
                 logger.info("Update student with name" + student.getName());
                 repository.save(newStudent);
                 return 0;
             } else{
                 logger.info("Create student with name" + student.getName());
                 repository.save(student);
             }

         } catch (IllegalArgumentException e){
             logger.info("illegal argument when create or update student" + student.getName());
             e.printStackTrace();
         }

        return 1;
    }

    @Override
    public int deleteStudentById(int id) {
        logger.info("delete student...");
         try{
             if (repository.existsById(String.valueOf(id))) {
                 repository.deleteById(String.valueOf(id));
                 return 0;
             }

         } catch(IllegalArgumentException e){
             logger.info("illegal argument when delete student" + String.valueOf(id));
             e.printStackTrace();
         }
         // does not exist, return 1
         return 1;
    }

    @Override
    public int createStudent(Student student){
         // so here we assume not allowing two duplicate name
         try{
             Optional<Student> studentInDataBase = repository.findOneByName(student.getName());
             if(!studentInDataBase.isPresent()) {
                 logger.info("create student with name" + student.getName());
                 repository.save(student);
                 return 0;
             }
             return 1;
        } catch (IllegalArgumentException e){
             logger.info("illegal argument when create student" + student.getName());
            e.printStackTrace();
        }
        return 1;
    }
}
