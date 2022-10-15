package com.example.IntegratedProject.dao;

import com.example.IntegratedProject.entity.Device;
import com.example.IntegratedProject.entity.TxBattery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TxBatteryRepository extends JpaRepository<TxBattery, Integer> {
    Optional<TxBattery> findTopByDeviceOrderByDateDesc(Device device);
}
