package com.storyestimationcore.score_estimation_core.service;

import com.storyestimationcore.score_estimation_core.domain.Developer;
import com.storyestimationcore.score_estimation_core.domain.Team;
import com.storyestimationcore.score_estimation_core.domain.User;
import com.storyestimationcore.score_estimation_core.domain.common.Status;
import com.storyestimationcore.score_estimation_core.exception.CustomException;
import com.storyestimationcore.score_estimation_core.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {
    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Team createTeam(Team team) {
        if (teamRepository.findByTeamName(team.getTeamName()) != null) {
            throw new CustomException("A team with the same name already exists");
        }

        try {
            team.setTeamName(team.getTeamName());
            team.getManager().setName(team.getManager().getName().toLowerCase());
            team.getProductOwner().setName(team.getProductOwner().getName().toLowerCase());
            team.getScrumMaster().setName(team.getScrumMaster().getName().toLowerCase());
            List<Developer> developers = team.getDevelopers();
            for (Developer developer : developers) {
                developer.setName(developer.getName().toLowerCase());
            }
            return teamRepository.save(team);
        } catch (DataIntegrityViolationException e) {
            throw new CustomException("Error creating team: " + e.getMessage());
        }
    }

    public boolean isUserMemberOfTeam(String teamName, String userName) {
        Team team = teamRepository.findByTeamName(teamName);
        if (team != null) {
            if (team.getTeamName().equalsIgnoreCase(userName) ||
                    team.getManager().getName().equalsIgnoreCase(userName) ||
                    team.getProductOwner().getName().equalsIgnoreCase(userName) ||
                    team.getScrumMaster().getName().equalsIgnoreCase(userName)) {
                return true;
            }

            List<Developer> developers = team.getDevelopers();
            return developers.stream()
                    .anyMatch(developer -> developer.getName().equalsIgnoreCase(userName));
        }
        return false;
    }

    public boolean updateUserScore(String teamName, String username, int score) {
        Team team = teamRepository.findByTeamName(teamName);
        if (team != null) {
            for (Developer developer : team.getDevelopers()) {
                if (developer.getName().equals(username)) {
                    developer.setScore(score);
                    teamRepository.save(team);
                    return true;
                }
            }
            if (team.getManager().getName().equals(username)) {
                team.getManager().setScore(score);
                teamRepository.save(team);
                return true;
            } else if (team.getProductOwner().getName().equals(username)) {
                team.getProductOwner().setScore(score);
                teamRepository.save(team);
                return true;
            } else if (team.getScrumMaster().getName().equals(username)) {
                team.getScrumMaster().setScore(score);
                teamRepository.save(team);
                return true;
            }
        }
        return false;
    }


    public Team findTeamByTeamName(String teamName) {
        Team team = teamRepository.findByTeamName(teamName);
        if (team == null) {
            throw new CustomException("Team not found with name: " + teamName);
        }
        return team;
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Team updateTeam(String teamId, Team updatedTeam) {
        Team existingTeam = teamRepository.findById(teamId)
                .orElseThrow(() -> new CustomException("Team not found with id: " + teamId));
        existingTeam.setTeamName(updatedTeam.getTeamName());
        existingTeam.setManager(updatedTeam.getManager());
        existingTeam.setProductOwner(updatedTeam.getProductOwner());
        existingTeam.setScrumMaster(updatedTeam.getScrumMaster());
        existingTeam.setDevelopers(updatedTeam.getDevelopers());

        return teamRepository.save(existingTeam);
    }

    public boolean deleteTeam(String teamName) {
        Team team = teamRepository.findByTeamName(teamName);
        if (team == null) {
            throw new CustomException("Team not found with name: " + teamName);
        }
        teamRepository.delete(team);
        return true;
    }

    public boolean resetEstimates(String teamName) {
        Team team = teamRepository.findByTeamName(teamName);
        if (team != null) {
            team.getManager().setScore(0);
            team.getProductOwner().setScore(0);
            team.getScrumMaster().setScore(0);
            for (Developer developer : team.getDevelopers()) {
                developer.setScore(0);
            }
            teamRepository.save(team);
            return true;
        }
        return false;
    }


    public boolean updateUserStatusToActive(String teamName, String username) {
        Team team = teamRepository.findByTeamName(teamName);
        if (team != null) {
            for (Developer developer : team.getDevelopers()) {
                if (developer.getName().equals(username)) {
                    developer.setStatus(String.valueOf(Status.ACTIVE));
                    teamRepository.save(team);
                    return true;
                }
            }
            if (team.getManager().getName().equals(username)) {
                team.getManager().setStatus(String.valueOf(Status.ACTIVE));
                teamRepository.save(team);
                return true;
            } else if (team.getProductOwner().getName().equals(username)) {
                team.getProductOwner().setStatus(String.valueOf(Status.ACTIVE));
                teamRepository.save(team);
                return true;
            } else if (team.getScrumMaster().getName().equals(username)) {
                team.getScrumMaster().setStatus(String.valueOf(Status.ACTIVE));
                teamRepository.save(team);
                return true;
            }
        }
        return false;
    }

    public boolean updateUserStatusToInActive(String teamName, String username) {
        Team team = teamRepository.findByTeamName(teamName);
        if (team != null) {
            for (Developer developer : team.getDevelopers()) {
                if (developer.getName().equals(username)) {
                    developer.setStatus(String.valueOf(Status.INACTIVE));
                    teamRepository.save(team);
                    return true;
                }
            }
            if (team.getManager().getName().equals(username)) {
                team.getManager().setStatus(String.valueOf(Status.INACTIVE));
                teamRepository.save(team);
                return true;
            } else if (team.getProductOwner().getName().equals(username)) {
                team.getProductOwner().setStatus(String.valueOf(Status.INACTIVE));
                teamRepository.save(team);
                return true;
            } else if (team.getScrumMaster().getName().equals(username)) {
                team.getScrumMaster().setStatus(String.valueOf(Status.INACTIVE));
                teamRepository.save(team);
                return true;
            }
        }
        return false;
    }

    public boolean showEstimations(String teamName) {
        Team team = teamRepository.findByTeamName(teamName);
        if (team != null) {
            team.setRevealEstimation(true);
            teamRepository.save(team);
            return true;
        }
        return false;

    }

    public boolean hideEstimations(String teamName) {
        Team team = teamRepository.findByTeamName(teamName);
        if (team != null) {
            team.setRevealEstimation(false);
            teamRepository.save(team);
            return true;
        }
        return false;
    }
}