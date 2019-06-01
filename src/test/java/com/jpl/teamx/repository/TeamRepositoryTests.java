package com.jpl.teamx.repository;

import static org.junit.Assert.assertEquals;

import com.jpl.teamx.model.Team;
import com.jpl.teamx.model.User;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TeamRepositoryTests {

    @Autowired
    private TeamRepository teamRepository;

    private User admin;
    private Team team;

    @Before
    public void setUp() {
        admin = new User("paolo","paolo@prova.com","testurl","imageurl");
        team = new Team(admin,"prova","prova desc", "prova loc");
        teamRepository.save(team);
    }

    @Test
    public void saveOneTeam() {

        Team found = teamRepository.findById(team.getId()).get();

        assertEquals(found.getId(), team.getId());
    }

    @Test
    public void findTeamByName() {

        Team found = teamRepository.findByName(team.getName());

        assertEquals(found.getId(), team.getId());
    }
}