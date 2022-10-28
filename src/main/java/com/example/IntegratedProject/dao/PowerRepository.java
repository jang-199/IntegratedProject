package com.example.IntegratedProject.dao;

import com.example.IntegratedProject.entity.Device;
import com.example.IntegratedProject.entity.Power;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PowerRepository extends JpaRepository<Power, Integer> {
    Optional<Power> findTopByDeviceOrderByDateDesc(Device device);
}
