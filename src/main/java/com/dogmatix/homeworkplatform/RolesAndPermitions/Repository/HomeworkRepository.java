package com.dogmatix.homeworkplatform.RolesAndPermitions.Repository;

import com.dogmatix.homeworkplatform.RolesAndPermitions.Model.Homework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HomeworkRepository extends JpaRepository<Homework, UUID> {
    List<Homework> findByClassId(UUID classId);
    List<Homework> findByCreatedBy(UUID teacherId);
}