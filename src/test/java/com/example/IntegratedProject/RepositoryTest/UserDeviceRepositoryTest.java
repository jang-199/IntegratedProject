//package com.example.IntegratedProject.RepositoryTest;
//
//import com.example.IntegratedProject.IntegratedProjectApplicationTests;
//import com.example.IntegratedProject.dao.UserDeviceRepository;
//import com.example.IntegratedProject.entity.UserDevice;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.Optional;
//
//public class UserDeviceRepositoryTest extends IntegratedProjectApplicationTests {
//
//    @Autowired
//    private UserDeviceRepository userRepository;
//
//    Integer id = 2;
//    String userId = "abcd";
//
//    @Test
//    public void register(){ // 카카오 로그인 api 쓰면 회원 등록이 필요가 없나!?
//        //given
//        UserDevice userDevice = new UserDevice();
//
//        userDevice.setId(id);
//        userDevice.setUserId(userId);
//
//
//        userRepository.save(userDevice);
//    }
//
//    @Test
//    public void search(){
//        Optional<UserDevice> user = userRepository.findById(id);
//
//        user.ifPresent(searchUser ->{
//            System.out.println("User: " +searchUser);
//        });
//    }
//
//    @Test
//    public void update(){
//        Optional<UserDevice> user = userRepository.findById(id);
//
//        user.ifPresent(updateUser ->{
//            updateUser.setUserId("efgh");
//            UserDevice newUserDevice = userRepository.save(updateUser);
//            System.out.println(newUserDevice);
//        });
//    }
//
//    /*
//    @Test
//    public void updateId(){
//        Optional<User> user = userRepository.findByUserId(userId);
//
//        user.ifPresent(updateUser -> {
//            updateUser.setDevice(new Device(12));
//            User newUser = userRepository.save(updateUser);
//            System.out.println(newUser);
//        });
//    }
//
//     */
//
//    @Test
//    public void delete(){
//        userRepository.deleteById(id);
//
//    }
//
//}
