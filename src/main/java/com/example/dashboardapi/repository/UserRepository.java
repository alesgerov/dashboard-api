package com.example.dashboardapi.repository;

import com.example.dashboardapi.entity.UserClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserClass, Long> {
    Optional<UserClass> findByEmail(String email);

    @Override
    Optional<UserClass> findById(Long aLong);
}
