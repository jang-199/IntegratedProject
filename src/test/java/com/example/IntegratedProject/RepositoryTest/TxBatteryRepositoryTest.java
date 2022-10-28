package com.example.IntegratedProject.RepositoryTest;

import com.example.IntegratedProject.IntegratedProjectApplicationTests;
import com.example.IntegratedProject.dao.TxBatteryRepository;
import com.example.IntegratedProject.entity.TxBattery;
import com.example.IntegratedProject.request.dto.BatteryDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class TxBatteryRepositoryTest extends IntegratedProjectApplicationTests {
    @Autowired
    private TxBatteryRepository txBatteryRepository;

    Integer id = 1;
    Integer lb = 70;

    @Test
    public void insert(BatteryDTO batteryDTO){
        TxBattery txBattery = new TxBattery();

        txBattery.setTx(batteryDTO.getBatteryCapacity());
        //lBattery.setDevice();
        txBatteryRepository.save(txBattery);
    }

    @Test
    public void search(){
        Optional<TxBattery> lBattery = txBatteryRepository.findById(id);

        lBattery.ifPresent(searchTxBattery ->{
            System.out.println("LBattery: " + searchTxBattery);
        });
    }

    @Test
    public void update(){
        Optional<TxBattery> lBattery = txBatteryRepository.findById(id);

        lBattery.ifPresent(updateTxBattery ->{
            updateTxBattery.setTx("90");
            TxBattery newTxBattery = txBatteryRepository.save(updateTxBattery);
            System.out.println(newTxBattery);
        });
    }

    @Test
    public void delete(){
        txBatteryRepository.deleteById(id);

    }
}
