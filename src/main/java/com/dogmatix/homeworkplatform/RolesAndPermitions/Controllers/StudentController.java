package com.dogmatix.homeworkplatform.RolesAndPermitions.Controllers;

import com.dogmatix.homeworkplatform.RolesAndPermitions.Model.Grade;
import com.dogmatix.homeworkplatform.RolesAndPermitions.Model.Submission;
import com.dogmatix.homeworkplatform.RolesAndPermitions.Model.User;
import com.dogmatix.homeworkplatform.RolesAndPermitions.Repository.GradeRepository;
import com.dogmatix.homeworkplatform.RolesAndPermitions.Repository.HomeworkRepository;
import com.dogmatix.homeworkplatform.RolesAndPermitions.Repository.SubmissionRepository;
import com.dogmatix.homeworkplatform.RolesAndPermitions.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

    private com.dogmatix.homeworkplatform.RolesAndPermitions.Model.Homework findHomeworkById(UUID homeworkId) {
        return homeworkRepository.findById(homeworkId)
                .orElseThrow(() -> new RuntimeException("Homework not found"));
    }

    //@PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/submit-homework")
    public ResponseEntity<?> submitHomework(@RequestBody Submission submission,
                               @AuthenticationPrincipal UserDetails loggedStudent) {

        //com.dogmatix.homeworkplatform.RolesAndPermitions.Model.Homework homework = findHomeworkById(submission.getHomeworkId());

        submission.setStudentId(((User)loggedStudent).getId());
        submission.setSubmittedAt(LocalDateTime.now());

      submission = submissionRepository.save(submission);
      return ResponseEntity.ok().body(submission);
    }

    //@PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/submitted-homeworks/{homeworkId}")
    public ResponseEntity<Map<String, Object>> viewHomework(@PathVariable UUID homeworkId,
                                                            @AuthenticationPrincipal UserDetails loggedStudent) {
        com.dogmatix.homeworkplatform.RolesAndPermitions.Model.Homework homework = homeworkRepository.findById(homeworkId)
                .orElseThrow(() -> new RuntimeException("Homework not found"));

        Submission submission = submissionRepository.findByHomeworkIdAndStudentId(homeworkId, ((User)loggedStudent).getId())
                .orElseThrow(() -> new RuntimeException("Submission not found"));

        Optional<Grade> grade = gradeRepository.findBySubmissionId(submission.getSubmissionId());

        Map<String, Object> response = new HashMap<>();
        response.put("homework", homework);
        response.put("submission", submission);
        response.put("grade", grade.orElse(null));

        return ResponseEntity.ok(response);
    }

}
