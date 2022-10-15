package com.example.IntegratedProject.request;

import com.example.IntegratedProject.dao.*;
import com.example.IntegratedProject.entity.*;
import com.example.IntegratedProject.request.dto.*;
import com.example.IntegratedProject.request.testobj.TestObj;
import com.example.IntegratedProject.service.DeviceService;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
public class DataController {
    private final RxBatteryRepository rxBatteryRepository;
    private final TxBatteryRepository txBatteryRepository;
    private final SensingRepository sensingRepository;
    private final PowerRepository powerRepository;
    private final UserDeviceRepository userDeviceRepository;
    private final DeviceRepository deviceRepository;
    private final DeviceService deviceService;
    private final UserPkRepository userPkRepository;

    @GetMapping
    String index() {
        return "/form/index";
    }

    @GetMapping("form/index")
    String formIndex() {
        return "form/index";
    }

    @GetMapping("/form/features")
    String features() {
        return "form/features";
    }

    @GetMapping("/form/contact")
    String contact() {
        return "form/contact";
    }

    @GetMapping("/form/user")
    String user(Model model){
        List<TestObj> list1 = new ArrayList<>();
        TestObj testObj1 = new TestObj();
        TestObj testObj2 = new TestObj();
        TestObj testObj3 = new TestObj();

        testObj1.setDeviceId("123");
        testObj2.setDeviceId("456");
        testObj3.setDeviceId("789");

        testObj1.setOnOff("On");
        testObj2.setOnOff("Off");
        testObj3.setOnOff("On");

        testObj1.setRxBattery("90");
        testObj2.setRxBattery("80");
        testObj3.setRxBattery("70");

        testObj1.setTxBattery("70");
        testObj2.setTxBattery("80");
        testObj3.setTxBattery("90");

        testObj1.setTime(LocalDateTime.now());
        testObj2.setTime(LocalDateTime.now());
        testObj3.setTime(LocalDateTime.now());

        testObj1.setVector("In");
        testObj2.setVector("Out");
        testObj3.setVector("In");

        list1.add(testObj1);
        list1.add(testObj2);
        list1.add(testObj3);

        model.addAttribute("test",list1);
        return "form/user";
    }

    @ResponseBody
    @PostMapping("/register/device") //기기 등록
    void deviceRegister(@RequestBody DeviceDTO deviceDTO){
        log.info("DeviceId:{}", deviceDTO.getDeviceId());

        Device device = new Device();

        device.setId(deviceDTO.getDeviceId());

        deviceService.register(device);
    }

    @ResponseBody
    @PostMapping("/register/user") //유저 등록
    void userRegister(@RequestBody UserDTO userDTO){
        log.info("UserPk:{}", userDTO.getUserPk());

        UserPk userPK = new UserPk();

        userPK.setUserPk(userDTO.getUserPk());

        userPkRepository.save(userPK);
    }

    @ResponseBody
    @PostMapping("/register/userdevice") //유저의 device 등록
    void userDeviceRegister(@RequestBody UserDTO userDTO) {
        log.info("userPk:{}, deviceId:{}", userDTO.getUserPk(), userDTO.getDeviceId());

        UserDevice userDevice = new UserDevice();

        if(deviceRepository.findById(userDTO.getDeviceId()).isPresent()){ //입력한 deviceId 가 DB에 존재한다면~
            userDevice.setUserPk(new UserPk(userDTO.getUserPk()));
            userDevice.setDevice(new Device(userDTO.getDeviceId()));

            userDeviceRepository.save(userDevice);
        } else {
            log.info("존재하지 않는 device 입니다."); //민혁님과 얘기해보기
        }
    }

    @ResponseBody
    @PostMapping("/delete/user") // 회원 탈퇴, UserPk 조회 후 유저의 전체 device 삭제
    void userDelete(@RequestBody UserDTO userDTO){
        log.info("userPk:{}", userDTO.getUserPk());

        UserPk userPK = new UserPk();

        userPK.setUserPk(userDTO.getUserPk());

        Optional<List<UserDevice>> deviceByUserPk = userDeviceRepository.findDeviceByUserPk(new UserPk(userDTO.getUserPk()));

        for(int i =0; i<deviceByUserPk.get().size(); i++){
            userDeviceRepository.delete(deviceByUserPk.get().get(i));
        }

        userPkRepository.delete(userPK);
    }

    @ResponseBody
    @PostMapping("/delete/user/option") // UserPk 조회 후, 유저의 device 등록 해제
    void userOptionalDelete(@RequestBody UserDTO userDTO){
        log.info("userPk:{}, index:{}", userDTO.getUserPk(), userDTO.getIndex());

        Optional<List<UserDevice>> userPk = userDeviceRepository.findByUserPk(new UserPk(userDTO.getUserPk()));
        UserDevice userDeviceIndex = userPk.get().get(userDTO.getIndex()-1); // 배열의 인덱스는 0부터 시작하므로 index-1을 해줌

        userDeviceRepository.delete(userDeviceIndex);
    }

    @ResponseBody
    @PostMapping("/update/rxbattery") //uno 보드에서 받아오는 배터리 정보
    void rxBattery(@RequestBody BatteryDTO batteryDTO) {
        log.info("RX배터리 용량:{}, deviceId:{}", batteryDTO.getBatteryCapacity(), batteryDTO.getDeviceId());

        RxBattery rxBattery = new RxBattery();

        rxBattery.setRx(batteryDTO.getBatteryCapacity());
        rxBattery.setDevice(new Device(batteryDTO.getDeviceId()));

        rxBatteryRepository.save(rxBattery);
    }

    @ResponseBody
    @PostMapping("/update/txbattery") //wemos 보드에서 받아오는 배터리 정보
    void txBattery(@RequestBody BatteryDTO batteryDTO) {
        log.info("TX배터리 용량:{},deviceId:{}", batteryDTO.getBatteryCapacity(),batteryDTO.getDeviceId());

        TxBattery txBattery = new TxBattery();

        txBattery.setTx(batteryDTO.getBatteryCapacity());
        txBattery.setDevice(new Device(batteryDTO.getDeviceId()));

        txBatteryRepository.save(txBattery);
    }

    @ResponseBody
    @PostMapping("/search/rxbattery") // uno 보드 최신 배터리 양 조회
    Integer searchRxBattery(@RequestBody BatteryDTO batteryDTO) {
        log.info("deviceId:{}", batteryDTO.getDeviceId());

        Optional<RxBattery> topByDeviceOrderByDateDesc = rxBatteryRepository.findTopByDeviceOrderByDateDesc(new Device(batteryDTO.getDeviceId()));

        Integer rx = topByDeviceOrderByDateDesc.get().getRx();

        return rx;
    }

    @ResponseBody
    @PostMapping("/search/txbattery") // wemos 보드 최신 배터리 양 조회
    Integer searchTxBattery(@RequestBody BatteryDTO batteryDTO) {
        log.info("deviceId:{}", batteryDTO.getDeviceId());

        Optional<TxBattery> topByDeviceOrderByDateDesc = txBatteryRepository.findTopByDeviceOrderByDateDesc(new Device(batteryDTO.getDeviceId()));

        Integer tx = topByDeviceOrderByDateDesc.get().getTx();

        return tx;
    }

//    @ResponseBody
//    @PostMapping("/update/power") // 아두이노 on off 정보
//    void power(@RequestBody PowerDTO powerDTO) {
//        log.info("작동 여부:{}, deviceId:{}", powerDTO.getPower(), powerDTO.getDeviceId());
//
//        Power power = new Power();
//
//        power.setPower(powerDTO.getPower().toString());
//        power.setDevice(new Device(powerDTO.getDeviceId()));
//
//        powerRepository.save(power);
//    }

    @ResponseBody
    @PostMapping("/search/power") // 아두이노 on off 최신 정보 조회
    String searchPower(@RequestBody PowerDTO powerDTO) {
        log.info("deviceId:{}", powerDTO.getDeviceId());

        Optional<Power> topByDeviceOrderByDateDesc = powerRepository.findTopByDeviceOrderByDateDesc(new Device(powerDTO.getDeviceId()));

        String power = topByDeviceOrderByDateDesc.get().getPower();

        return power;
    }

    @ResponseBody
    @PostMapping("/update/sensing") // 살짝 바꿔야 할듯 요소들이 상당히 많아
    void sensing(@RequestBody SensingDTO sensingDTO) {
        log.info("출입 방향:{}, deviceId:{}", sensingDTO.getState(), sensingDTO.getDeviceId());// 로그 다시 찍기

        Sensing sensing = new Sensing();
        Power power = new Power();

        power.setPower(sensingDTO.getPower().toString());
        power.setDevice(new Device(sensingDTO.getDeviceId()));

        sensing.setState(sensingDTO.getState().toString());
        sensing.setDevice(new Device(sensingDTO.getDeviceId()));
        sensing.setUserPk(new UserPk(sensingDTO.getUserPk()));
        sensing.setPower(power);

        sensingRepository.save(sensing);
    }

    @ResponseBody
    @PostMapping("/search/app")//App으로 넘겨주는 정보. 특정 Device를 기준으로 최신 순으로 조회
    String searchApp(@RequestBody DeviceDTO deviceDTO) {
        log.info("deviceId:{}", deviceDTO.getDeviceId());

        JsonArray obj = new JsonArray(); // Json 들이 들어갈 Array 선언

        Integer deviceIdParam = deviceDTO.getDeviceId();

        List<Sensing> deviceIdOrderByDateDesc = sensingRepository.findTop20ByDeviceOrderByDateDesc(new Device(deviceIdParam)).get();
        //디바이스 값을 받아 DB에서 최신 순으로 20개 찾기

        Iterator<Sensing> iterator = deviceIdOrderByDateDesc.iterator();//iterator() : 리스트의 데이터를 반복하는 반복자 객체를 선언

        while(iterator.hasNext()){ // 리스트의 다음 인덱스가 존재하면 true 반환 (검사만 진행)
            Sensing next = iterator.next(); // 인덱스 값을 반환하고 다음 인덱스로 커서를 옮김 (반환 값 리턴)

            JsonObject jsonObject = new JsonObject(); //받아오는 객체를 Json 객체로 변환

            jsonObject.addProperty("deiceId",next.getDevice().getId());
            jsonObject.addProperty("state",next.getState());
            jsonObject.addProperty("date",next.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            jsonObject.addProperty("power",next.getPower().getPower());

            obj.add(jsonObject);
        }
        return obj.toString();
    }
    @ResponseBody
    @PostMapping("/search/web")
    String searchWeb(@RequestBody UserDTO userDTO){//Web으로 넘겨주는 정보. 유저가 선택한 날짜를 기준으로 최신 순으로 조회
        log.info("UserPk:{}, LocalDate:{}", userDTO.getUserPk(), userDTO.getLocalDate());

        String userPkParam = userDTO.getUserPk();
        LocalDate localDate = userDTO.getLocalDate();

        List<Sensing> sensing = sensingRepository.findByUserPkAndLocalDateOrderByDateDesc(new UserPk(userPkParam), localDate).get();
        //UserPK 값과 날짜를 받아 DB에서 최신 순으로 찾기

        Iterator<Sensing> iterator = sensing.iterator(); // 리스트의 데이터 담고 반복하는 반복자 객체를 선언

        JsonArray obj = new JsonArray();// Json 들이 들어갈 Array 선언

        while (iterator.hasNext()){
            Sensing next = iterator.next(); // 인덱스 값을 반환하고 다음 인덱스로 커서를 옮김 (반환 값 리턴)

            JsonObject jsonObject = new JsonObject(); //받아오는 객체를 Json 객체로 변환

            jsonObject.addProperty("deiceId",next.getDevice().getId());
            jsonObject.addProperty("state",next.getState());
            jsonObject.addProperty("date",next.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            jsonObject.addProperty("power",next.getPower().getPower());

            obj.add(jsonObject);
        }
        return obj.toString();
    }
}
