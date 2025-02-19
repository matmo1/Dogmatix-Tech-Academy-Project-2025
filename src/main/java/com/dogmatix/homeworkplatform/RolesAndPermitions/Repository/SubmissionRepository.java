package com.dogmatix.homeworkplatform.RolesAndPermitions.Repository;

import com.dogmatix.homeworkplatform.RolesAndPermitions.Model.Enrollment;
import com.dogmatix.homeworkplatform.RolesAndPermitions.Model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SubmissionRepository extends JpaRepository<Submission, UUID> {
    Optional<Submission> findByHomeworkIdAndStudentId(UUID homeworkId, UUID studentId);
}
