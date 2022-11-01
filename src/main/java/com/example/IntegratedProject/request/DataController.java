package com.example.IntegratedProject.request;

import com.example.IntegratedProject.dao.*;
import com.example.IntegratedProject.entity.*;
import com.example.IntegratedProject.request.dto.*;
import com.example.IntegratedProject.service.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.*;

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
    private final UserPkRepository userPkRepository;
    private final DeviceService deviceService;
    private final SensingService sensingService;

    @GetMapping
    String index() {
        return "index";
    }

    @GetMapping("features")
    String features() {
        return "features";
    }

    @GetMapping("contact")
    String contact() {
        return "contact";
    }

    @GetMapping("user")
    String user() {
        return "user";
    }

    @ResponseBody
    @PostMapping("/register/device") // 기기 등록
    void deviceRegister(@RequestBody DeviceDTO deviceDTO){
        log.info("deviceId 등록 : {}", deviceDTO.getDeviceId());

        Device device = new Device();

        device.setId(deviceDTO.getDeviceId());

        deviceService.register(device);
    }

    @ResponseBody
    @PostMapping("/register/user") // 유저 등록
    String userRegister(@RequestBody UserDTO userDTO){
        log.info("userPk 등록 : {}", userDTO.getUserPk());

        UserPk userPK = new UserPk();

        JsonObject jsonObject = new JsonObject(); // 받아오는 객체를 Json 객체로 변환

        userPK.setUserPk(userDTO.getUserPk());

        userPkRepository.save(userPK);

        jsonObject.addProperty("okSign","Ok");

        return jsonObject.toString(); // App에게 OK 사인 리턴
    }

    @ResponseBody
    @PostMapping("/register/userdevice") // 유저의 device 등록
    String userDeviceRegister(@RequestBody UserDTO userDTO) {
        log.info("userPk의 : {}, deviceId 등록 : {}", userDTO.getUserPk(), userDTO.getDeviceId());

        UserDevice userDevice = new UserDevice();

        JsonObject jsonObject = new JsonObject();

        if(deviceRepository.findById(userDTO.getDeviceId()).isPresent()){ // 입력한 deviceId가 DB에 존재하는지 검사
            if (!userDeviceRepository.findByUserPkAndDevice(new UserPk(userDTO.getUserPk()), new Device(userDTO.getDeviceId())).isPresent()){ // 특정 UserPk가 같은 Device 등록 방지
                userDevice.setUserPk(new UserPk(userDTO.getUserPk()));
                userDevice.setDevice(new Device(userDTO.getDeviceId()));

                userDeviceRepository.save(userDevice);
            }
            else {
                log.info("이미 등록된 device 입니다.");
                jsonObject.addProperty("okSign","deviceDuplicate");
                return jsonObject.toString();
            }
        } else {
            log.info("존재하지 않는 device 입니다.");
            jsonObject.addProperty("okSign","deviceNotFound");
            return jsonObject.toString();
        }

        jsonObject.addProperty("okSign","Ok");
        return jsonObject.toString();
    }

    @ResponseBody
    @PostMapping("/delete/user") // 회원 탈퇴, UserPk 조회 후 유저의 전체 device 삭제
    String userDelete(@RequestBody UserDTO userDTO){
        log.info("userPk 탈퇴 : {}", userDTO.getUserPk());

        UserPk userPK = new UserPk();

        JsonObject jsonObject = new JsonObject();

        userPK.setUserPk(userDTO.getUserPk());

        Optional<List<UserDevice>> deviceByUserPk = userDeviceRepository.findDeviceByUserPk(new UserPk(userDTO.getUserPk()));

        for(int i =0; i<deviceByUserPk.get().size(); i++){
            userDeviceRepository.delete(deviceByUserPk.get().get(i));
        }

        userPkRepository.delete(userPK);

        jsonObject.addProperty("okSign","Ok");

        return jsonObject.toString();
    }

    @ResponseBody
    @PostMapping("/delete/user/option") // UserPk 조회 후, 유저의 device 등록 해제
    String userOptionalDelete(@RequestBody UserDTO userDTO){
        log.info("userPk의 : {}, deviceId 해제 : {}", userDTO.getUserPk(), userDTO.getDeviceId());

        Optional<UserDevice> byUserPkAndDevice = userDeviceRepository.findByUserPkAndDevice(new UserPk(userDTO.getUserPk()), new Device(userDTO.getDeviceId()));

        UserDevice userDevice = byUserPkAndDevice.get();

        JsonObject jsonObject = new JsonObject();

        userDeviceRepository.delete(userDevice);

        jsonObject.addProperty("okSign","Ok");

        return jsonObject.toString();
    }

    @ResponseBody
    @GetMapping("/update/rxbattery") // uno 보드에서 받아오는 배터리 정보
    void rxBattery(@RequestParam(value = "batteryCapacity") String batteryCapacity,
                   @RequestParam(value = "deviceId") String deviceId) {
        log.info("deviceId : {}, RX배터리 용량 : {} ", deviceId, batteryCapacity);

        RxBattery rxBattery = new RxBattery();

        rxBattery.setRx(batteryCapacity);
        rxBattery.setDevice(new Device(deviceId));

        rxBatteryRepository.save(rxBattery);
    }

    @ResponseBody
    @PostMapping("/update/txbattery") // wemos 보드에서 받아오는 배터리 정보
    void txBattery(@RequestBody BatteryDTO batteryDTO) {
        log.info("deviceId : {}, TX배터리 용량 : {} ", batteryDTO.getDeviceId(), batteryDTO.getBatteryCapacity());

        TxBattery txBattery = new TxBattery();

        txBattery.setTx(batteryDTO.getBatteryCapacity());
        txBattery.setDevice(new Device(batteryDTO.getDeviceId()));

        txBatteryRepository.save(txBattery);
    }

    @ResponseBody
    @PostMapping("/search/rxbattery") // uno 보드 최신 배터리 양 조회
    String searchRxBattery(@RequestBody BatteryDTO batteryDTO) {
        log.info("rx - deviceId : {}", batteryDTO.getDeviceId());

        Optional<RxBattery> topByDeviceOrderByDateDesc = rxBatteryRepository.findTopByDeviceOrderByDateDesc(new Device(batteryDTO.getDeviceId()));

        JsonObject jsonObject = new JsonObject();

        String rx = topByDeviceOrderByDateDesc.get().getRx();

        jsonObject.addProperty("rx", rx);

        return jsonObject.toString();
    }

    @ResponseBody
    @PostMapping("/search/txbattery") // wemos 보드 최신 배터리 양 조회
    String searchTxBattery(@RequestBody BatteryDTO batteryDTO) {
        log.info("tx - deviceId : {}", batteryDTO.getDeviceId());

        Optional<TxBattery> topByDeviceOrderByDateDesc = txBatteryRepository.findTopByDeviceOrderByDateDesc(new Device(batteryDTO.getDeviceId()));

        JsonObject jsonObject = new JsonObject();

        String tx = topByDeviceOrderByDateDesc.get().getTx();

        jsonObject.addProperty("tx", tx);

        return jsonObject.toString();
    }

    @ResponseBody
    @PostMapping("/search/power") // 아두이노 on off 최신 정보 조회
    String searchPower(@RequestBody PowerDTO powerDTO) {
        log.info("power - deviceId : {}", powerDTO.getDeviceId());

        Optional<Power> topByDeviceOrderByDateDesc = powerRepository.findTopByDeviceOrderByDateDesc(new Device(powerDTO.getDeviceId()));

        JsonObject jsonObject = new JsonObject();

        String power = topByDeviceOrderByDateDesc.get().getPower();

        jsonObject.addProperty("power", power);

        return jsonObject.toString();
    }

    @ResponseBody
    @GetMapping("/update/sensing") // uno 보드에서 받아오는 정보들
    void sensing(@RequestParam(value = "state") String state,
                 @RequestParam(value = "deviceId") String deviceId,
                 @RequestParam(value = "power") String power) {
        log.info("sensing - deviceId : {}, power: {}, 출입 방향 : {}", deviceId, power, state);

        Sensing sensing = new Sensing();
        Power power1 = new Power();

        power1.setPower(power);
        power1.setDevice(new Device(deviceId));

        sensing.setState(state); // In, Out 정보
        sensing.setDevice(new Device(deviceId)); // Device Id 값
        sensing.setPower(power1); // On, Off 정보 및 Power Entity Cascade로 생성

        sensingRepository.save(sensing);
    }

    @ResponseBody
    @PostMapping("/search/app") // App으로 넘겨주는 정보. 특정 Device를 기준으로 최신 순으로 조회
    String searchApp(@RequestBody DeviceDTO deviceDTO) {
        log.info("App - deviceId : {}", deviceDTO.getDeviceId());

        JsonArray obj = new JsonArray(); // Json 들이 들어갈 Array 선언

        String deviceIdParam = deviceDTO.getDeviceId();

        List<Sensing> deviceIdOrderByDateDesc = sensingRepository.findTop20ByDeviceOrderByDateDesc(new Device(deviceIdParam)).get();
        // 디바이스 값을 받아 DB에서 최신 순으로 20개 찾기

        Iterator<Sensing> iterator = deviceIdOrderByDateDesc.iterator(); // iterator() : 리스트의 데이터를 반복하는 반복자 객체를 선언

        while(iterator.hasNext()){ // 리스트의 다음 인덱스가 존재하면 true 반환 (검사만 진행)
            Sensing next = iterator.next(); // 인덱스 값을 반환하고 다음 인덱스로 커서를 옮김 (반환 값 리턴)

            JsonObject jsonObject = new JsonObject(); // 받아오는 객체를 Json 객체로 변환

            jsonObject.addProperty("deviceId",next.getDevice().getId());
            jsonObject.addProperty("state",next.getState());
            jsonObject.addProperty("date",next.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            jsonObject.addProperty("power",next.getPower().getPower());

            obj.add(jsonObject);
        }
        return obj.toString();
    }

    @GetMapping("/search/web") // Web으로 넘겨주는 정보. 유저가 선택한 날짜를 기준으로 최신 순으로 조회
    String searchWeb(@RequestParam(value = "userPk", required = false)  String userPk,
                     @RequestParam(value = "datetimepicker1Input", required = false) String datetimepicker1Input,
                     @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                     Model model){

        log.info("Web - UserPk : {}, LocalDate : {}, Page : {}", userPk, datetimepicker1Input, page);

        model.addAttribute("userPk", userPk);
        model.addAttribute("LocalDate", datetimepicker1Input);

        Optional<List<UserDevice>> deviceByUserPk = userDeviceRepository.findDeviceByUserPk(new UserPk(userPk));
        //입력한 userPk로 UserDevice 테이블에서 deviceId 리스트로 가져온 것.

        if(userPk != null && datetimepicker1Input != null){
            String year = datetimepicker1Input.substring(0,4);
            String month = datetimepicker1Input.substring(6,8);
            String date = datetimepicker1Input.substring(10,12);

            log.info("year : {}, month : {}, date : {}", year, month, date);

            LocalDate of = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(date));

            List<Sensing> sensings = new ArrayList<>();

            for(int i = 0; i<deviceByUserPk.get().size(); i++){ // 유저가 가진 디바이스 개수에 따른 반복
                Device oneDevice = deviceByUserPk.get().get(i).getDevice();

                List<Sensing> sensing = sensingRepository.findByDeviceAndLocalDateOrderByDateDesc(new Device(oneDevice.getId()), of).get();
                // 웹에서 로그인 한 User가 가지고 있는 기기들을 날짜 최신순으로 리스트에 저장

                sensings.addAll(sensing); // 모든 리스트들을 저장
            }

            Collections.sort(sensings, new Comparator<Sensing>() { // 사용자가 가진 기기가 여러개 일 때 날짜 최신 순으로 sorting
                @Override
                public int compare(Sensing s1, Sensing s2) {
                    return s1.getDate().compareTo(s2.getDate());
                }
            }.reversed());

            log.info("sensings:{}", sensings); // 소팅된 리스트 목록

            int totalPage = (int) Math.ceil((double) sensings.size()/(double) 10); // 전체 페이지 개수

            log.info("totalPage:{}", totalPage);

            model.addAttribute("totalPage", totalPage);

            int fixCount = 10; // 한 페이지에 보여줄 리스트 개수

            List<Sensing> sensingList = sensingService.paging(sensings, fixCount, page); // 페이징

            model.addAttribute("fixCount", fixCount);

            model.addAttribute("sensingList", sensingList);

            log.info("sensingList:{}", sensingList); // 페이징 된 리스트 목록

            int pageSize = 5; // 하단 페이지 번호 개수
            int tempStartPage = (int) Math.floor((double) page / (double) pageSize) * pageSize; // 특정 페이지 리스트 목록의 시작 번호

            int tempEndPage = tempStartPage + pageSize - 1; // 특정 페이지 리스트 목록의 마지막 번호

            if (tempEndPage>totalPage){
                tempEndPage = totalPage-1;
            }

            int right = (int) Math.floor((double) (page+pageSize) / (double) pageSize) * pageSize; // 오른쪽 버튼 눌렀을 때 이동 페이지 번호
            int left = (int) Math.floor((double) (page-pageSize) / (double) pageSize) * pageSize; // 왼쪽 버튼 눌렀을 때 이동 페이지 번호

            model.addAttribute("pageSize", pageSize);
            model.addAttribute("tempStartPage", tempStartPage);
            model.addAttribute("tempEndPage", tempEndPage);
            model.addAttribute("right", right);
            model.addAttribute("left", left);

            return "user";
        }

        else { return "user"; }
    }
}