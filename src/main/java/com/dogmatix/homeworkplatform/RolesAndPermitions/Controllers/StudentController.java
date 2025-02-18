package com.dogmatix.homeworkplatform.RolesAndPermitions.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentController {

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/view-grades")
    public String viewGrades() {
        return "Student viewing grades";
    }
}
