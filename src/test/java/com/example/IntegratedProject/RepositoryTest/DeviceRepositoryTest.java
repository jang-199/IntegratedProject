package com.example.IntegratedProject.RepositoryTest;

import com.example.IntegratedProject.IntegratedProjectApplicationTests;
import com.example.IntegratedProject.dao.DeviceRepository;
import com.example.IntegratedProject.entity.Device;

import com.example.IntegratedProject.service.DeviceService;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


//@SpringBootTest
public class DeviceRepositoryTest extends IntegratedProjectApplicationTests {

    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private DeviceService deviceService;
    String deviceId = "1";
    //DeviceService deviceService = new DeviceService();


    @Test
    //@Transactional
    public void insert(){
        //given
        Device device = new Device();
        device.setId(deviceId);

        //when
        String saveId = deviceService.register(device);

        int i = Integer.parseInt(saveId);

        //then
        Device findDevice = deviceService.findOne(i).get();
        Assertions.assertThat(device.getId()).isEqualTo(findDevice.getId()); //검증법

        //Device result = deviceRepository.findByid(device.getId()).get();
        //deviceRepository.flush(); //jpa 커넥션 풀 따와서
    }

    @Test
    public void duplication(){ //중복검사
        //given
        Device device1 = new Device();
        device1.setId("2");

        Device device2 = new Device();
        device2.setId("2");

        //when
        deviceService.register(device1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> deviceService.register(device2));

        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 기기입니다.");

        /*
        try{
            deviceService.register(device2);
            fail();//예외가 발생해야합니다.
        } catch (IllegalStateException e){
            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 기기입니다.");
        }
         */
    }

    @Test
    public void search(){
        Optional<Device> device = deviceRepository.findById(deviceId);

        device.ifPresent(searchDevice ->{
            System.out.println("Device: " +searchDevice);
        });
    }

    @Test
    public void update(){
        Optional<Device> device = deviceRepository.findById(deviceId);

        device.ifPresent(updateDevice ->{
            updateDevice.setId("1234");
            Device newDevice = deviceRepository.save(updateDevice);
            System.out.println(newDevice);
        });


    }

    @Test
    public void delete(){
        Device device = new Device();

        device.setId(deviceId);

        deviceRepository.delete(device);
    }

    @Test
    public void findTopByOrderByIdDesc(){
        Device device = new Device();

        device = deviceRepository.findTopByOrderByIdDesc();

        System.out.println(device);
    }



}
