package com.dogmatix.homeworkplatform.RolesAndPermitions.Controllers;

import com.dogmatix.homeworkplatform.RolesAndPermitions.Model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

import com.dogmatix.homeworkplatform.RolesAndPermitions.Model.Homework;
import com.dogmatix.homeworkplatform.RolesAndPermitions.Repository.HomeworkRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class HomeworkController {

    @Autowired
    private HomeworkRepository homeworkRepository;

    @GetMapping("/homeworks/{homeworkUuid}")
    public ResponseEntity<Homework> getSpecificHomework(@PathVariable UUID homeworkUuid) {
    Optional<Homework> homework = homeworkRepository.findById(homeworkUuid);
    return homework.map(ResponseEntity::ok)
                  .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/homeworks")
    public ResponseEntity<?> postMethodName(@RequestBody Homework homework,
                                            @AuthenticationPrincipal UserDetails teacher) {
        try {
            homework.setCreatedBy(((User)teacher).getId());
            homework.setDeadline(LocalDateTime.now().plusDays(10));
            Homework saved = homeworkRepository.save(homework);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Failed to save homework: " + e.getMessage());
        }
    }
    
    @PutMapping("/homeworks/{id}")
    public ResponseEntity<?> updateHomework(
            @PathVariable UUID id,
            @RequestBody Homework homework) {

        Optional<Homework> optionalHomework = homeworkRepository.findById(id);

        if (optionalHomework.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Homework not found");
        }

        Homework existingHomework = optionalHomework.get();

        existingHomework.setTitle(homework.getTitle());
        existingHomework.setDescription(homework.getDescription());
        if (homework.getDeadline() != null)
            existingHomework.setDeadline(homework.getDeadline());
        existingHomework.setIsPublished(homework.getIsPublished());
        
        Homework updatedHomework = homeworkRepository.save(existingHomework);

        return ResponseEntity.ok(updatedHomework);
    }

    @DeleteMapping("/homeworks/{id}")
    public ResponseEntity<?> deleteHomework(@PathVariable UUID id) {
        Optional<Homework> optionalHomework = homeworkRepository.findById(id);

        if (optionalHomework.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Homework not found");
        }

        homeworkRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
