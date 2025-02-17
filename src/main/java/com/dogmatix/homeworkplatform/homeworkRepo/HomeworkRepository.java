package com.dogmatix.homeworkplatform.homeworkRepo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dogmatix.homeworkplatform.homework.Homework;

public interface HomeworkRepository extends JpaRepository<Homework, UUID> {
}
