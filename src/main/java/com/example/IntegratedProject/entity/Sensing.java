package com.example.IntegratedProject.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor //파라미터가 없는 생성자를 생성
@AllArgsConstructor //클래스에 존재하는 모든 필드에 대한 생성자를 자동으로 생성.
@ToString
@Entity
public class Sensing {
    @Id  // Primary Key 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // AUTO_INCREMENT 설정 (id값이 null일 경우 자동 생성)
    @Column(name = "ID")  // 컬럼 지정
    private Integer id;

    @Column(name = "STATE")
    private String state;

    @NotNull
    @Column(name = "DATE")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date = LocalDateTime.now();

    @NotNull
    @Column(name = "LOCALDATE") // 사실 이 엔티티도 필요가 없을거같은데
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate localDate = date.toLocalDate();

    @ManyToOne//(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "DEVICE_ID")//어떤 column과 연결이 될 지 설정
    private Device device;

    @ManyToOne //센싱되는 시점에서의 USER값을 알기 위해 조인
    @JoinColumn(name = "USER_PK")
    private UserPk userPk;

    @ManyToOne(cascade = CascadeType.PERSIST) //센싱되는 시점에서의 POWER값을 알기 위해 조인
    @JoinColumn(name = "POWER")
    private Power power;
//    @ManyToOne //센싱되는 시점에서의 RB값을 알기 위해 조인
//    @JoinColumn(name = "RB")
//    private RBattery rbattery;
//
//    @ManyToOne //센싱되는 시점에서의 LB값을 알기 위해 조인
//    @JoinColumn(name = "LB")
//    private LBattery lbattery;


}
