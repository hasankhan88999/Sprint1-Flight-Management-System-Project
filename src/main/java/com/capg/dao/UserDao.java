package com.capg.dao;
import org.springframework.data.repository.CrudRepository;

import com.capg.entity.Users;

public interface UserDao extends CrudRepository<Users, Integer>{

}