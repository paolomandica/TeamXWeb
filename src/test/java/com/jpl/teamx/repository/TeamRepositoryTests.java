package com.jpl.teamx.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import com.jpl.teamx.model.Team;
import com.jpl.teamx.model.User;

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

    @Test
    public void saveOneTeam() {

        User admin = new User("paolo","paolo@prova.com","testurl","imageurl");
        Team team = new Team(admin,"prova nome","prova desc", "prova loc");

        teamRepository.save(team);
        Team found = teamRepository.findById(team.getId()).get();

        assertEquals(found.getId(), team.getId());
    }
}