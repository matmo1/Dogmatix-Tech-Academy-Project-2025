package com.dogmatix.homeworkplatform.RolesAndPermitions.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class SubmissionService {
    @Autowired
    private SubmissionRepo submissionRepo;

    public List<Submission> getSubmissionsByStudentId(UUID studentId) {
        return submissionRepo.findByStudentId(studentId);
    }

    public Submission saveSubmission(Submission submission) {
        return submissionRepo.save(submission);
    }
}