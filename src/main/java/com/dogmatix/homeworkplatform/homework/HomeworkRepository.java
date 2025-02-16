package com.dogmatix.homeworkplatform.homework;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeworkRepository extends JpaRepository<Homework, UUID> {
}
