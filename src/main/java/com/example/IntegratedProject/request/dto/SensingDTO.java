package com.example.IntegratedProject.request.dto;

import lombok.Data;

@Data
public class SensingDTO {
    Integer deviceId;
    String userPk;
    PowerForm power;
    //Integer battery
    SensingForm state;

}
