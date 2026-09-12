package com.stars.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.stars.domain.entities.UserEntity;

@Repository 
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

}
