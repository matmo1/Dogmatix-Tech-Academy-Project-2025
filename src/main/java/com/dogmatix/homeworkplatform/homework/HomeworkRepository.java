package com.dogmatix.homeworkplatform.homework;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeworkRepository extends JpaRepository<Homework, String> {
}
