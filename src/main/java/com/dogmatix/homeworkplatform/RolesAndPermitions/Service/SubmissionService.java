package com.dogmatix.homeworkplatform.RolesAndPermitions.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dogmatix.homeworkplatform.RolesAndPermitions.Model.Submission;
import com.dogmatix.homeworkplatform.RolesAndPermitions.Repository.SubmissionRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class SubmissionService {
    @Autowired
    private SubmissionRepository submissionRepo;

    public Optional<Submission> getSubmissionsByStudentId(UUID studentId) {
        return submissionRepo.findById(studentId);
    }

    public Submission saveSubmission(Submission submission) {
        return submissionRepo.save(submission);
    }
}