package com.example.IntegratedProject.request.dto;

import lombok.Data;

@Data
public class PowerDTO {
    String deviceId;
    PowerForm power;

//    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
//    LocalDateTime time;
}
