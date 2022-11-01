package com.example.IntegratedProject.dao;

import com.example.IntegratedProject.entity.Device;
import com.example.IntegratedProject.entity.Sensing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

public interface SensingRepository extends JpaRepository<Sensing, Integer> {
    Optional<List<Sensing>> findTop20ByDeviceOrderByDateDesc(Device device);
    Optional<List<Sensing>> findByDeviceAndLocalDateOrderByDateDesc(Device device, LocalDate localDate);
    Optional<List<Sensing>> findByDateLessThan(LocalDateTime localDateTime);
}
