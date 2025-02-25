package com.dogmatix.homeworkplatform.RolesAndPermitions.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.dogmatix.homeworkplatform.RolesAndPermitions.Model.Submission;
import com.dogmatix.homeworkplatform.RolesAndPermitions.Service.SubmissionService;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/submissions")
public class SubmissionController {
    @Autowired
    private SubmissionService submissionService;

    @GetMapping("/student/{studentId}")
    public Optional<Submission> getSubmissionsByStudentId(@PathVariable UUID studentId) {
        return submissionService.getSubmissionsByStudentId(studentId);
    }

    @PostMapping
    public Submission createSubmission(@RequestBody Submission submission) {
        return submissionService.saveSubmission(submission);
    }
}