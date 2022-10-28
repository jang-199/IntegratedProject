package com.example.IntegratedProject.service;

import com.example.IntegratedProject.dao.SensingRepository;
import com.example.IntegratedProject.entity.Device;
import com.example.IntegratedProject.entity.Sensing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SensingService {
    @Autowired
    SensingRepository sensingRepository;

    public Page<Sensing> list(int page){ // 페이징 처리
        return sensingRepository.findAll(PageRequest.of(page,10)); // 한 페이지에 들어갈 리스트 갯수 : 10
    }

    public Page<List<Sensing>> list1(Device device, LocalDate localDate, int page){ // 페이징 처리
        return sensingRepository.findByDeviceAndLocalDateOrderByDateDesc(device, localDate, PageRequest.of(page,10)); // 한 페이지에 들어갈 리스트 갯수 : 10
    }

//    public Page<Sensing> list(List<Sensing> sensings, int page){ // 페이징 처리
//        return sensingRepository.findAllBy(sensings, PageRequest.of(page,10)); // 한 페이지에 들어갈 리스트 갯수 : 10
//    }
}
