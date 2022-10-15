package com.example.IntegratedProject.dao;

import com.example.IntegratedProject.entity.UserPk;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserPkRepository extends JpaRepository<UserPk, String> {
}
