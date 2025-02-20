package com.dogmatix.homeworkplatform.RolesAndPermitions.Controllers;

import com.dogmatix.homeworkplatform.RolesAndPermitions.Model.*;
import com.dogmatix.homeworkplatform.RolesAndPermitions.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    private final HomeworkRepository homeworkRepository;
    private final SubmissionRepository submissionRepository;
    private final GradeRepository gradeRepository;
    private final ClassRepository classRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;

    @Autowired
    public TeacherController(HomeworkRepository homeworkRepository,
                             SubmissionRepository submissionRepository,
                             GradeRepository gradeRepository,
                             ClassRepository classRepository,
                             EnrollmentRepository enrollmentRepository,
                             UserRepository userRepository) {
        this.homeworkRepository = homeworkRepository;
        this.submissionRepository = submissionRepository;
        this.gradeRepository = gradeRepository;
        this.classRepository = classRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/homeworks")
    public Homework createHomework(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam LocalDateTime deadline,
            @RequestParam UUID classId,
            @AuthenticationPrincipal User teacher) {

        Enrollment enrollment = enrollmentRepository.findByUserIdAndClassId(teacher.getId(), classId)
                .orElseThrow(() -> new RuntimeException("Not authorized for this class"));

        if (enrollment.getRole() != Enrollment.Role.TEACHER) {
            throw new RuntimeException("Not a teacher in this class");
        }

        Homework homework = new Homework();
        homework.setTitle(title);
        homework.setDescription(description);
        homework.setDeadline(deadline);
        homework.setClassId(classId);
        homework.setCreatedBy(teacher.getId());

        return homeworkRepository.save(homework);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PutMapping("/homeworks/{homeworkId}/publish")
    public Homework publishHomework(
            @PathVariable UUID homeworkId,
            @AuthenticationPrincipal User teacher) {

        Homework homework = homeworkRepository.findById(homeworkId)
                .orElseThrow(() -> new RuntimeException("Homework not found"));

        if (!homework.getCreatedBy().equals(teacher.getId())) {
            throw new RuntimeException("Not the homework owner");
        }

        homework.setIsPublished(true);
        return homeworkRepository.save(homework);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/submissions/{submissionId}/grade")
    public Grade gradeSubmission(
            @PathVariable UUID submissionId,
            @RequestParam BigDecimal score,
            @RequestParam String feedback,
            @AuthenticationPrincipal User teacher) {

        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Submission not found"));

        Homework homework = homeworkRepository.findById(submission.getHomeworkId())
                .orElseThrow(() -> new RuntimeException("Homework not found"));

        Enrollment enrollment = enrollmentRepository.findByUserIdAndClassId(teacher.getId(), homework.getClassId())
                .orElseThrow(() -> new RuntimeException("Not authorized"));

        if (enrollment.getRole() != Enrollment.Role.TEACHER) {
            throw new RuntimeException("Not a teacher in this class");
        }

        Grade grade = new Grade();
        grade.setSubmissionId(submissionId);
        grade.setScore(score);
        grade.setFeedback(feedback);
        grade.setGradedBy(teacher.getId());

        return gradeRepository.save(grade);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/classes")
    public List<Map<String, Object>> getAssignedClasses(
            @AuthenticationPrincipal User teacher) {

        List<Map<String, Object>> result = new ArrayList<>();

        List<Enrollment> teacherEnrollments = enrollmentRepository.findByUserIdAndRole(
                teacher.getId(),
                Enrollment.Role.TEACHER
        );

        for (Enrollment enrollment : teacherEnrollments) {
            ClassEntity classEntity = classRepository.findById(enrollment.getClassId())
                    .orElseThrow(() -> new RuntimeException("Class not found"));

            List<Enrollment> studentEnrollments = enrollmentRepository.findByClassIdAndRole(
                    enrollment.getClassId(),
                    Enrollment.Role.STUDENT
            );

            List<User> students = userRepository.findAllById(
                    studentEnrollments.stream()
                            .map(Enrollment::getUserId)
                            .toList()
            );

            Map<String, Object> classData = new HashMap<>();
            classData.put("class", classEntity);
            classData.put("students", students);
            result.add(classData);
        }

        return result;
    }
}