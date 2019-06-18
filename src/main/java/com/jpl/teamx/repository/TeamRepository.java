package com.jpl.teamx.repository;

import java.util.List;

import com.jpl.teamx.model.Team;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TeamRepository extends CrudRepository<Team, Long> {

    List<Team> findAllByOrderByTimestampDesc();

}