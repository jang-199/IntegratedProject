package com.example.IntegratedProject.service;

import com.example.IntegratedProject.dao.RxBatteryRepository;
import com.example.IntegratedProject.dao.SensingRepository;
import com.example.IntegratedProject.dao.TxBatteryRepository;

import com.example.IntegratedProject.entity.RxBattery;
import com.example.IntegratedProject.entity.Sensing;
import com.example.IntegratedProject.entity.TxBattery;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SchedulerService {
    private final RxBatteryRepository rxBatteryRepository;
    private final TxBatteryRepository txBatteryRepository;
    private final SensingRepository sensingRepository;

    @Scheduled(cron = "0 0 0 * * *") // 매일 0시 0분에 실행
    public void DBController(){ // 계속해서 쌓이는 DB 삭제 로직

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime monthAgo = now.minusMonths(3); // 현재로부터 세 달 전 시각

        Optional<List<RxBattery>> rxDelete = rxBatteryRepository.findByDateLessThan(monthAgo);
        Optional<List<TxBattery>> txDelete = txBatteryRepository.findByDateLessThan(monthAgo);
        Optional<List<Sensing>> sensingDelete = sensingRepository.findByDateLessThan(monthAgo);

        rxBatteryRepository.deleteAll(rxDelete.get());
        txBatteryRepository.deleteAll(txDelete.get());
        sensingRepository.deleteAll(sensingDelete.get());
    }
}
