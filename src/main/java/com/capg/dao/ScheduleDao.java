package com.capg.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.capg.entity.Schedule;

@Repository
public interface ScheduleDao extends CrudRepository<Schedule, Integer> {

}
