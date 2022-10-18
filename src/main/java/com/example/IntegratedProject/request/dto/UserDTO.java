package com.example.IntegratedProject.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDTO {
    String userPk;
    String deviceId;
    Integer index; //몇 번 째 기기를 등록/삭제 할 지에 대한 값

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    LocalDate localDate;
}
