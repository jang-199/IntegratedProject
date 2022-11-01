package com.example.IntegratedProject.dao;

import com.example.IntegratedProject.entity.Device;
import com.example.IntegratedProject.entity.RxBattery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RxBatteryRepository extends JpaRepository<RxBattery, Integer> {
    Optional<RxBattery> findTopByDeviceOrderByDateDesc(Device device);
//    Optional<List<RxBattery>> findByDeviceOrderByDate(Device device);
    Optional<List<RxBattery>> findByDateLessThan(LocalDateTime localDateTime);
}
