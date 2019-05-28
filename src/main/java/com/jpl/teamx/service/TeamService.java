package com.jpl.teamx.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.jpl.teamx.model.Team;
import com.jpl.teamx.repository.TeamRepository;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;
    
    public Team createTeam(User admin, String nome, String desc, String location) {
        Team t = new Team(admin, nome, desc, location);
        return teamRepository.save(t);
    }

    public void deleteTeam(Team team) {
        teamRepository.delete(team);
    }

    public Team getTeam(Long id) {
        return teamRepository.findById(id).orElse(null);
    }

    public Map<Long,Team> getAllTeams() {
        Collection<Team> teamsCollection = teamRepository.findAll();
        Map<Long,Team> teams = new HashMap<>();

        for (Team t : teamsCollection) {
            teams.put(t.getId(), t);
        }
        return teams;
    }
}