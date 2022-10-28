package com.example.IntegratedProject.request.dto;

import lombok.Data;

@Data
public class SensingDTO {
    String deviceId;
    String userPk;
    PowerForm power;
    SensingForm state;
}
