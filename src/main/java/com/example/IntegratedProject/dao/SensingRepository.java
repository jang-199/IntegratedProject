package com.example.IntegratedProject.dao;

import com.example.IntegratedProject.entity.Device;
import com.example.IntegratedProject.entity.Sensing;
import com.example.IntegratedProject.entity.UserPk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SensingRepository extends JpaRepository<Sensing, Integer> {
    Optional<List<Sensing>> findTop20ByDeviceOrderByDateDesc(Device device);
    Optional<List<Sensing>> findByUserPkAndLocalDateOrderByDateDesc(UserPk userPk, LocalDate localDate);
}
