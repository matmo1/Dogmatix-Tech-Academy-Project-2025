package com.dogmatix.homeworkplatform.RolesAndPermitions.Repository;

import com.dogmatix.homeworkplatform.RolesAndPermitions.Model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {
    Optional<Enrollment> findByUserIdAndClassId(UUID userId, UUID classId);
    List<Enrollment> findByUserIdAndRole(UUID userId, Enrollment.Role role);
    List<Enrollment> findByClassIdAndRole(UUID classId, Enrollment.Role role);
}
