package com.example.IntegratedProject.RepositoryTest;

import com.example.IntegratedProject.IntegratedProjectApplicationTests;
import com.example.IntegratedProject.dao.PowerRepository;
import com.example.IntegratedProject.entity.Power;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class PowerRepositoryTest extends IntegratedProjectApplicationTests {

    @Autowired
    private PowerRepository powerRepository;

    Integer id = 2;
    String state = "On";

    @Test
    public void insert(){
        Power power = new Power();

        power.setPower(state);
        //power.setDate(new Date());
        //power.setDevice();

        powerRepository.save(power);
    }



    @Test
    public void search(){
        Optional<Power> power = powerRepository.findById(id);

        power.ifPresent(searchPower ->{
            System.out.println("Power: " +searchPower);
        });
    }

    @Test
    public void update(){
        Optional<Power> power = powerRepository.findById(id);

        power.ifPresent(updatePower ->{
            updatePower.setPower("Off");
            Power newPower = powerRepository.save(updatePower);
            System.out.println(newPower);
        });
    }

    @Test
    public void delete(){
        powerRepository.deleteById(id);

    }

}
