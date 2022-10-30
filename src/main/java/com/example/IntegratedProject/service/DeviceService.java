package com.example.IntegratedProject.service;

import com.example.IntegratedProject.dao.DeviceRepository;
import com.example.IntegratedProject.entity.Device;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceService { // 비즈니스 로직 - 중복 검사

    @Autowired
    private DeviceRepository deviceRepository;

    //회원가입
    public String register(Device device){
        //같은 deviceId 중복시 등록 x
        checkDevice(device); // 중복 회원 검증
        deviceRepository.save(device);
        return device.getId();
    }

    private void checkDevice(Device device) {
        deviceRepository.findById(device.getId())
                .ifPresent(d ->{
            throw new IllegalStateException("이미 존재하는 기기입니다.");
        });
    }

    //전체 기기 조회
    public List<Device> findDevices(){
        return deviceRepository.findAll();
    }

    public Optional<Device> findOne(Integer id){
        return deviceRepository.findById(id);
    }
}
