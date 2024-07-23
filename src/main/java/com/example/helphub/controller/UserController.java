//package com.example.helphub.controller;
//
//
//import com.example.helphub.dto.UserDTO;
//import com.example.helphub.responsedtos.UserResponseDTO;
//import com.example.helphub.entity.User;
//import com.example.helphub.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import jakarta.validation.Valid;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/users")
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//    @GetMapping
//    public List<UserDTO> getAllUsers() {
//        return userService.findAll();
//    }
//
////    @GetMapping("/{id}")
////    public User getUserById(@PathVariable Long id) {
////        return userService.findById(id);
////    }
//
//    @PostMapping("/create")
//    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
//        userService.save(user);
//        return new ResponseEntity<>(user,HttpStatus.OK);
//    }
//
//    @PutMapping("/{id}")
//    public User updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
//        return userService.update(id, userDTO);
//    }
//
//    @PutMapping("/update/{email}")
//    public UserDTO updateUserbyEmail(@PathVariable String email,@Valid @RequestBody UserDTO userDTO){
//        return userService.updateByEmail(email,userDTO);
//    }
//
////    @DeleteMapping("/{id}")
////    public void deleteUser(@PathVariable Long id) {
////        userService.delete(id);
////    }
//
//    @DeleteMapping("/delete/{email}")
//    public ResponseEntity<?> deleteByEmail(@PathVariable String email){
//        if(userService.deleteByEmail(email)){
//        return new ResponseEntity<>("Record Deleted",HttpStatus.OK);
//    }
//        return null;
//    }
//
//
//    //custom query
//    @GetMapping("/{username}")
//    public UserResponseDTO getUserByUsername(@PathVariable String username) {
//        return userService.findByUsername(username);
//    }
//
//    @GetMapping("/email/{email}")
//    public UserResponseDTO getUserByEmail(@PathVariable String email) {
//        return userService.findByEmail(email);
//    }
//}



package com.example.helphub.controller;

import com.example.helphub.dto.UserDTO;
import com.example.helphub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("/username/{username}")
    public UserDTO getUserByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }

    @GetMapping("/email/{email}")
    public UserDTO getUserByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }

    @PostMapping
    public UserDTO createUser(@Valid @RequestBody UserDTO userDTO) {
        return userService.save(userDTO);
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        return userService.update(id, userDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }
}
