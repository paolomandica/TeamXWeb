package com.jpl.teamx.service;

import java.util.List;

import com.jpl.teamx.form.AddTeamForm;
import com.jpl.teamx.model.Team;
import com.jpl.teamx.repository.TeamRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;
    
    public Team addTeam(AddTeamForm atf) {
        Team t = new Team(atf.getAdmin(), atf.getName(), atf.getDescription(), atf.getLocation());
        return teamRepository.save(t);
    }

    public void deleteTeam(Team team) {
        teamRepository.delete(team);
    }

    public Team getTeam(Long id) {
        return teamRepository.findById(id).orElse(null);
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }
}