package com.example.universityexample.repository;

import com.example.universityexample.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

    // to do: change it to email or something
    Optional<Student> findOneByName(String name);
}
