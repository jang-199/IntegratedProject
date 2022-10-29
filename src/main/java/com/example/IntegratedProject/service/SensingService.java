package com.example.IntegratedProject.service;

import com.example.IntegratedProject.dao.SensingRepository;
import com.example.IntegratedProject.entity.Device;
import com.example.IntegratedProject.entity.Sensing;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SensingService {
    public List<Sensing> paging(List<Sensing> sensings, int fixCount, int page) { // 페이징 처리
        int numberOfSize = sensings.size();
        int startRow = page * fixCount; // 시작
        int endRow =  startRow + fixCount;// 끝
        int remainder = numberOfSize % fixCount; // 나머지
        int totalPage = (int) Math.ceil(((double) numberOfSize)/ (double) fixCount); // 전체 페이지 개수

        List<Sensing> sensingList = new ArrayList<>();

        if (page + 1 != totalPage){ // 맨 마지막 이전 페이지까지의 계산
            List<Sensing> sensings1 = sensings.subList(startRow, endRow);

            sensingList.addAll(sensings1);
        }
        else { // 맨 마지막 페이지 일 때의 계산
            startRow = fixCount * page;
            endRow = startRow + remainder;

            List<Sensing> sensings1 = sensings.subList(startRow, endRow);

            sensingList.addAll(sensings1);
        }

        return sensingList;
    }

}
