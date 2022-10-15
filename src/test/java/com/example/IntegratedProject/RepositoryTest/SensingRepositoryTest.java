package com.example.IntegratedProject.RepositoryTest;

import com.example.IntegratedProject.IntegratedProjectApplicationTests;
import com.example.IntegratedProject.dao.SensingRepository;
import com.example.IntegratedProject.entity.Device;
import com.example.IntegratedProject.entity.Sensing;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class SensingRepositoryTest extends IntegratedProjectApplicationTests {

    @Autowired
    private SensingRepository sensingRepository;

    Integer id = 1;
    String state = "In";
    @Test
    public void insert(){
        Sensing sensing = new Sensing();

        sensing.setId(id);
        sensing.setState(state);
        //sensing.setDate(new Date());
        //sensing.setDevice();

        Sensing newSensing = sensingRepository.save(sensing);
        System.out.println(newSensing);
    }

    @Test
    public void search(){
        Optional<Sensing> sensing = sensingRepository.findById(id);

        sensing.ifPresent(searchSensing ->{
            System.out.println("Sensing: " +searchSensing);
        });
    }

    @Test
    public void update(){
        Optional<Sensing> sensing = sensingRepository.findById(id);

        sensing.ifPresent(updateSensing ->{
            updateSensing.setState("Out");
            Sensing newSensing = sensingRepository.save(updateSensing);
            System.out.println(newSensing);
        });
    }

    @Test
    public void delete(){

        sensingRepository.deleteById(id);

    }

}
