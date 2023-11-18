package com.storyestimationcore.score_estimation_core.Check;

import com.storyestimationcore.score_estimation_core.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checks")
@CrossOrigin(origins = "*" , allowedHeaders = "*")
public class CheckController {
    private final CheckService checkService;

    @Autowired
    public CheckController(CheckService checkService) {
        this.checkService = checkService;
    }

    @GetMapping("/checkin/{username}")

    public ResponseEntity<?> checkIn(@PathVariable String username) {
        try {
            Check check = checkService.checkIn(username.toUpperCase());
            return ResponseEntity.ok(check);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/checkout/{username}")
    public ResponseEntity<?> checkOut(@PathVariable String username) {
        try {
            Check check = checkService.checkOut(username.toUpperCase());
            return ResponseEntity.ok(check);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    @PostMapping("/create")
    public ResponseEntity<?> createCheck(@RequestBody Check check) {
        try {
            Check newCheck = checkService.createCheck(check);
            return ResponseEntity.ok(newCheck); // Return the newly created user if needed.
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findCheckById(@PathVariable String id) {
        try {
            Check check = checkService.getCheckById(id).orElseThrow(() -> new CustomException("Check not found"));
            return ResponseEntity.ok(check);
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllChecks() {
        List<Check> checks = checkService.getAllChecks();
        if (checks.isEmpty()) {
            return ResponseEntity.ok("No checks found");
        } else {
            return ResponseEntity.ok(checks);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCheck(@PathVariable String id, @RequestBody Check updatedCheck) {
        try {
            Check check = checkService.updateCheck(id, updatedCheck);
            return ResponseEntity.ok(check);
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCheck(@PathVariable String id) {
        try {
            checkService.deleteCheck(id);
            return ResponseEntity.ok("Check deleted successfully");
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}