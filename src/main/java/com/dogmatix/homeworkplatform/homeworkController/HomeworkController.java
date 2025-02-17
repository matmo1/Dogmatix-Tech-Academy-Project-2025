package com.dogmatix.homeworkplatform.homeworkController;

import org.springframework.web.bind.annotation.RestController;

import com.dogmatix.homeworkplatform.homework.Homework;
import com.dogmatix.homeworkplatform.homeworkRepo.HomeworkRepository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
public class HomeworkController {

    @Autowired
    private HomeworkRepository homeworkRepository;

    @GetMapping("/homework")
    public ResponseEntity<Homework> getSpecificHomework(@RequestParam UUID homeworkUuid) {
    Optional<Homework> homework = homeworkRepository.findById(homeworkUuid);
    return homework.map(ResponseEntity::ok)
                  .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public String postMethodName(@RequestBody Homework homework) {
        //TODO: process POST request
        homeworkRepository.save(homework);
        return "Saved...";
    }
    
}
