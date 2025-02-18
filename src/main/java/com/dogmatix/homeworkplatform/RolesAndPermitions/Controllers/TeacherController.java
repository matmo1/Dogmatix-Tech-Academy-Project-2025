package com.dogmatix.homeworkplatform.RolesAndPermitions.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/assign-homework")
    public String assignHomework() {
        return "Teacher assigning homework";
    }
}
