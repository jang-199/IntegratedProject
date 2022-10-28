package com.example.IntegratedProject.RepositoryTest;

import com.example.IntegratedProject.IntegratedProjectApplicationTests;
import com.example.IntegratedProject.dao.RxBatteryRepository;
import com.example.IntegratedProject.entity.RxBattery;
import com.example.IntegratedProject.request.dto.BatteryDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class RxBatteryRepositoryTest extends IntegratedProjectApplicationTests {

    @Autowired
    private RxBatteryRepository rxBatteryRepository;

    Integer id = 1;

    @Test
    public void insert(BatteryDTO batteryDTO){
        RxBattery rxBattery = new RxBattery();

        rxBattery.setRx(batteryDTO.getBatteryCapacity());
        //rBattery.setDevice();
        rxBatteryRepository.save(rxBattery);
    }

    @Test
    public void search(){
        Optional<RxBattery> rBattery = rxBatteryRepository.findById(id);

        rBattery.ifPresent(searchRxBattery ->{
            System.out.println("RBattery: " + searchRxBattery);
        });
    }

    @Test
    public void update(){
        Optional<RxBattery> rBattery = rxBatteryRepository.findById(id);

        rBattery.ifPresent(updateRxBattery ->{
            updateRxBattery.setRx("90");
            RxBattery newRxBattery = rxBatteryRepository.save(updateRxBattery);
            System.out.println(newRxBattery);
        });
    }

    @Test
    public void delete(){
        rxBatteryRepository.deleteById(id);

    }
}
