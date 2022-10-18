package com.example.IntegratedProject.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor //파라미터가 없는 생성자를 생성
@AllArgsConstructor //클래스에 존재하는 모든 필드에 대한 생성자를 자동으로 생성.
@ToString
@Entity
public class RxBattery {
    @Id  // Primary Key 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // AUTO_INCREMENT 설정 (id값이 null일 경우 자동 생성)
    @Column(name = "ID")  // 컬럼 지정
    private Integer id;

    @NotNull
    @Column(name = "RX_BATTERY")
    private String rx;

    @NotNull
    @Column(name = "DATE")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date = LocalDateTime.now();

    @ManyToOne//(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "DEVICE_ID")//어떤 column과 연결이 될 지 설정
    private Device device;
}
