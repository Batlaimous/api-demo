package com.besho.restapi.crm_api.rest;

import com.besho.restapi.crm_api.entity.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentRestController {
    private List<Student> students;

    @PostConstruct
    public void loadStudents() {
        students = new ArrayList<>();
        students.add(new Student("Beshoy","younan"));
        students.add(new Student("Mario","Rossi"));
        students.add(new Student("Adriano","Araujo"));
        students.add(new Student("Aline","Araujo"));
        students.add(new Student("Matheus","Reis"));
    }

    // define endpoint for "/students"
    @GetMapping("/students")
    public List<Student> getStudents() {
        return students;
    }

    @GetMapping("/students/{studentId}")
    public Student getStudent(@PathVariable int studentId) {

        // chack the studentId againest the list size
        if (studentId >= 0 && studentId < students.size()) {
            // just index into the list ... keep it simple for now later will add id
            return students.get(studentId);
        } else {
            throw new StudentNotFoundException("Student not found :-  "+ studentId);
        }
    }
    // add an exception handler using @Exceptionhandler
    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(StudentNotFoundException exc) {
        // create a studentErrorResponse
        StudentErrorResponse error = new StudentErrorResponse();
        error.setMessage(exc.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setTimestamp(System.currentTimeMillis());
        // return the response entity
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

    }
}
