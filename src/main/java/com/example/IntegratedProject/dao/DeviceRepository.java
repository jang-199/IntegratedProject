package com.example.IntegratedProject.dao;

import com.example.IntegratedProject.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, String> {
    Optional<Device> findById(Integer id); //Optional이란 'null일 수도 있는 객체'를 감싸는 일종의 Wrapper 클래스
    List<Device> findAll();
    Device findTopByOrderByIdDesc();

}
