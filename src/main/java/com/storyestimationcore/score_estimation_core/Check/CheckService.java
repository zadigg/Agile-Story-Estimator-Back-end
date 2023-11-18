package com.storyestimationcore.score_estimation_core.Check;

import com.storyestimationcore.score_estimation_core.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckService {
    private final CheckRepository checkRepository;

    public Check createCheck(Check check) {
        if (check == null) {
            throw new CustomException("Invalid Check payload");
        }

        if (check.getUsername() == null) {
            throw new CustomException("Username is empty");
        }

        if (checkRepository.existsByUsername(check.getUsername().toUpperCase())) {
            throw new CustomException("User already exists");
        }
        check.setUsername(check.getUsername().toUpperCase());
        check.setStatus("Inactive");

        return checkRepository.save(check);
    }

    public List<Check> getAllChecks() {
        return checkRepository.findAll();
    }

    public Optional<Check> getCheckById(String id) {
        return checkRepository.findById(id);
    }

    public Check updateCheck(String id, Check updatedCheck) {
        Optional<Check> existingCheck = checkRepository.findById(id);
        if (existingCheck.isEmpty()) {
            throw new CustomException("Check not found");
        }

        existingCheck.get().setUsername(updatedCheck.getUsername());

        return checkRepository.save(existingCheck.get());
    }

    public void deleteCheck(String id) {
        if (!checkRepository.existsById(id)) {
            throw new CustomException("Check not found");
        }
        checkRepository.deleteById(id);
    }

    public Check checkIn(String username) {
        if(username == null) {
            throw new CustomException("Invalid username");
        }

        Check check = checkRepository.findByUsername(username);
        if(check == null) {
            throw new CustomException("User not found");
        }

        check.setStatus("Active");

        return checkRepository.save(check);

    }

    public Check checkOut(String username) {
        if(username == null) {
            throw new CustomException("Invalid username");
        }

        Check check = checkRepository.findByUsername(username);
        if(check == null) {
            throw new CustomException("User not found");
        }

        check.setStatus("Inactive");

        return checkRepository.save(check);

    }
}