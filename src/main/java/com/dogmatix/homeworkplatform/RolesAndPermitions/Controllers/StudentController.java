package com.dogmatix.homeworkplatform.RolesAndPermitions.Controllers;

import com.dogmatix.homeworkplatform.RolesAndPermitions.Model.Grade;
import com.dogmatix.homeworkplatform.RolesAndPermitions.Model.Homework;
import com.dogmatix.homeworkplatform.RolesAndPermitions.Model.Submission;
import com.dogmatix.homeworkplatform.RolesAndPermitions.Model.User;
import com.dogmatix.homeworkplatform.RolesAndPermitions.Repository.GradeRepository;
import com.dogmatix.homeworkplatform.homeworkRepo.HomeworkRepository;
import com.dogmatix.homeworkplatform.RolesAndPermitions.Repository.SubmissionRepository;
import com.dogmatix.homeworkplatform.RolesAndPermitions.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final HomeworkRepository homeworkRepository;
    @Autowired
    private final SubmissionRepository submissionRepository;
    @Autowired
    private final GradeRepository gradeRepository;


    public StudentController(UserRepository userRepository, HomeworkRepository homeworkRepository, SubmissionRepository submissionRepository, GradeRepository gradeRepository) {
        this.userRepository = userRepository;
        this.homeworkRepository = homeworkRepository;
        this.submissionRepository = submissionRepository;
        this.gradeRepository = gradeRepository;
    }

    private User findUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private com.dogmatix.homeworkplatform.homework.Homework findHomeworkById(UUID homeworkId) {
        return homeworkRepository.findById(homeworkId)
                .orElseThrow(() -> new RuntimeException("Homework not found"));
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/submit-homework/{homeworkId}")
    public void submitHomework(@PathVariable UUID homeworkId,
                               @RequestParam UUID studentId,
                               @RequestParam String content,
                               @RequestParam String attachment_url) {
        User student = findUserById(studentId);

        com.dogmatix.homeworkplatform.homework.Homework homework = findHomeworkById(homeworkId);

        Submission submission = new Submission();
        submission.setHomeworkId(homework.getHomeworkId());
        submission.setStudentId(student.getId());
        submission.setContent(content);
        submission.setAttachmentUrl(attachment_url);
        submission.setSubmittedAt(LocalDateTime.now());

        submissionRepository.save(submission);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/view-homework/{homeworkId}")
    public ResponseEntity<Map<String, Object>> viewHomework(@PathVariable UUID homeworkId,
                                                            @RequestParam UUID studentId) {
        // Fetch homework details
        com.dogmatix.homeworkplatform.homework.Homework homework = homeworkRepository.findById(homeworkId)
                .orElseThrow(() -> new RuntimeException("Homework not found"));

        // Fetch submission
        Submission submission = submissionRepository.findByHomeworkIdAndStudentId(homeworkId, studentId)
                .orElseThrow(() -> new RuntimeException("Submission not found"));

        // Fetch grade
        Optional<Grade> grade = gradeRepository.findBySubmissionId(submission.getSubmissionId());

        // Prepare response
        Map<String, Object> response = new HashMap<>();
        response.put("homework", homework);
        response.put("submission", submission);
        response.put("grade", grade.orElse(null));

        return ResponseEntity.ok(response);
    }

}
