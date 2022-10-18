package com.example.IntegratedProject.request.dto;

import lombok.Data;

@Data
public class BatteryDTO {
    String deviceId;
    String batteryCapacity;

//    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
//    LocalDateTime time;
}
