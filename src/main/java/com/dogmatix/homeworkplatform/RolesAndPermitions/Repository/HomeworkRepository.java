package com.dogmatix.homeworkplatform.RolesAndPermitions.Repository;

import com.dogmatix.homeworkplatform.RolesAndPermitions.Model.Homework;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface HomeworkRepository extends JpaRepository<Homework, UUID> {
    List<Homework> findByClassId(UUID classId);
    List<Homework> findByCreatedBy(UUID teacherId);
}