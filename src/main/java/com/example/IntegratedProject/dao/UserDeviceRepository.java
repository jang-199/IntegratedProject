package com.example.IntegratedProject.dao;

import com.example.IntegratedProject.entity.UserDevice;
import com.example.IntegratedProject.entity.UserPk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserDeviceRepository extends JpaRepository<UserDevice, String>{
    Optional<List<UserDevice>> findByUserPk(UserPk userPk);
    Optional<List<UserDevice>> findDeviceByUserPk(UserPk userPk);
}
