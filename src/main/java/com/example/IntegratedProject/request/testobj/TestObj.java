package com.example.IntegratedProject.request.testobj;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class TestObj {
    LocalDateTime time;
    String deviceId;
    String vector;
    String txBattery;
    String rxBattery;
    String OnOff;
}
