package com.storyestimationcore.score_estimation_core.controller;

import com.storyestimationcore.score_estimation_core.domain.Team;
import com.storyestimationcore.score_estimation_core.dto.ValidateMembershipRequest;
import com.storyestimationcore.score_estimation_core.dto.ValidateMembershipResponse;
import com.storyestimationcore.score_estimation_core.exception.CustomException;
import com.storyestimationcore.score_estimation_core.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/teams")
public class TeamController {
    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTeam(@RequestBody Team team) {
        try {
            Team newTeam = teamService.createTeam(team);
            return ResponseEntity.ok(newTeam); // Return the newly created team if needed.
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/validateMembership")
    public ResponseEntity<?> validateMembership(@RequestBody ValidateMembershipRequest request) {
        boolean isMember = teamService.isUserMemberOfTeam(request.getTeamName(), request.getUserName());
        return ResponseEntity.ok(new ValidateMembershipResponse(isMember));
    }

    @PutMapping("/{teamName}/users/{username}/score/{score}")
    public ResponseEntity<?> updateUserScore(
            @PathVariable String teamName,
            @PathVariable String username,
            @PathVariable int score) {
        try {
            boolean updated = teamService.updateUserScore(teamName, username, score);
            if (updated) {
                return ResponseEntity.ok("User score updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or team not found");
            }
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/updateStatus/active/{teamName}/users/{username}")
    public ResponseEntity<?> updateUserStatusToActive(@PathVariable String teamName, @PathVariable String username) {
        try {
            boolean updated = teamService.updateUserStatusToActive(teamName, username.toLowerCase());
            if (updated) {
                return ResponseEntity.ok("User status updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or team not found");
            }
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/updateStatus/inactive/{teamName}/users/{username}")
    public ResponseEntity<?> updateUserStatusToInactive(@PathVariable String teamName, @PathVariable String username) {
        try {
            boolean updated = teamService.updateUserStatusToInActive(teamName, username.toLowerCase());
            if (updated) {
                return ResponseEntity.ok("User status updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or team not found");
            }
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{teamName}/resetEstimates")
    public ResponseEntity<?> resetEstimates(@PathVariable String teamName) {
        try {
            boolean updated = teamService.resetEstimates(teamName);
            if (updated) {
                return ResponseEntity.ok("Estimates reset successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Team not found");
            }
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{teamName}/showEstimations")
    public ResponseEntity<?> showEstimations(@PathVariable String teamName) {
        try {
            boolean updated = teamService.showEstimations(teamName);
            if (updated) {
                return ResponseEntity.ok("Estimates shown successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Team not found");
            }
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{teamName}/hideEstimations")
    public ResponseEntity<?> hideEstimations(@PathVariable String teamName) {
        try {
            boolean updated = teamService.hideEstimations(teamName);
            if (updated) {
                return ResponseEntity.ok("Estimates hidden successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Team not found");
            }
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/find/{teamName}")
    public ResponseEntity<?> findTeamByTeamName(@PathVariable String teamName) {
        try {
            Team team = teamService.findTeamByTeamName(teamName);
            return ResponseEntity.ok(team);
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllTeams() {
        List<Team> teams = teamService.getAllTeams();
        if (teams.isEmpty()) {
            return ResponseEntity.ok("No teams found");
        } else {
            return ResponseEntity.ok(teams);
        }
    }

    @PutMapping("/update/{teamId}")
    public ResponseEntity<?> updateTeam(@PathVariable String teamId, @RequestBody Team updatedTeam) {
        try {
            Team team = teamService.updateTeam(teamId, updatedTeam);
            return ResponseEntity.ok(team);
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{teamName}")
    public ResponseEntity<?> deleteTeam(@PathVariable String teamName) {
        try {
            boolean deleted = teamService.deleteTeam(teamName);
            return ResponseEntity.ok("Team deleted successfully");
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
