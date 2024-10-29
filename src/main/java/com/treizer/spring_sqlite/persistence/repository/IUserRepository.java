package com.treizer.spring_sqlite.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.treizer.spring_sqlite.persistence.entity.UserEntity;

@Repository
public interface IUserRepository extends CrudRepository<UserEntity, Long> {
}
