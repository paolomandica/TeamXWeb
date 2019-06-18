package com.jpl.teamx.repository;

import static org.junit.Assert.assertEquals;

import com.jpl.teamx.model.Team;
import com.jpl.teamx.model.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TeamRepositoryTests {

    @Autowired
    private TeamRepository teamRepository;

    private User admin;
    private Team team1;
    private Team team2;

    @Before
    public void setUp() {
        admin = new User("paolo","paolo@prova.com","testurl", null, null);
        team1 = new Team(admin,"prova","prova desc", "prova loc","");
        team2 = new Team(admin, "prova2", "prova desc 2", "prova loc 2","") ;
        teamRepository.save(team1);
        teamRepository.save(team2);
    }

    @Test
    public void saveOneTeam() {

        Team found = teamRepository.findById(team1.getId()).get();

        assertEquals(found.getId(), team1.getId());
    }

    @Test
    public void findTeamByName() {

       // Team found = teamRepository.findByName(team1.getName());

       // assertEquals(found.getId(), team1.getId());
    }

    @Test
    public void getAllTeams() {
        List<Team> teams = (List<Team>) teamRepository.findAll();

        assertEquals(teams.size(), 2);
    }
}