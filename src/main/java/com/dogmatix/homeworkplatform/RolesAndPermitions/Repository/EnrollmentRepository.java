package com.dogmatix.homeworkplatform.RolesAndPermitions.Repository;

import com.dogmatix.homeworkplatform.RolesAndPermitions.Model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {
    Optional<Enrollment> findByUserIdAndClassId(UUID userId, UUID classId);
}
