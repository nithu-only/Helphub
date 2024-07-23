//package com.example.helphub.service;
//
//import com.example.helphub.dto.UserDTO;
//import com.example.helphub.responsedtos.UserResponseDTO;
//import com.example.helphub.exception.ResourceNotFoundException;
//import com.example.helphub.entity.User;
//import com.example.helphub.repository.UserRepository;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class UserService {
//    @Autowired
//    private UserRepository userRepository;
//
//    public List<UserDTO> findAll() {
//        List<User> users = userRepository.findAll();
//        List<UserDTO> userDTOS = new ArrayList<>();
//        for(User user:users){
//            userDTOS.add(entityToDto(user));
//        }
//        return userDTOS;
//    }
//
//    public UserDTO entityToDto(User user){
//        UserDTO userDTO = new UserDTO();
//        userDTO.setUsername(user.getUsername());
//        userDTO.setEmail(user.getEmail());
//        return userDTO;
//    }
//
//    public User findById(Long id) {
//        return userRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
//    }
//
//    public void save(User user) {
//        userRepository.save(user);
//    }
//
//    public User update(Long id, UserDTO userDTO) {
//        User user = findById(id);
//        user.setUsername(userDTO.getUsername());
//        user.setEmail(userDTO.getEmail());
//        return userRepository.save(user);
//    }
//
//    public UserDTO updateByEmail(String email, UserDTO userDTO) {
//        User user = userRepository.findUserByEmail(email)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found with email " + email));
//        user.setUsername(userDTO.getUsername());
//        user.setEmail(userDTO.getEmail());
//        userRepository.save(user);
//        return entityToDto(user);
//    }
//
//    public void delete(Long id) {
//        User user = findById(id);
//        userRepository.delete(user);
//    }
//
//    public boolean deleteByEmail(String email) {
//        User user = userRepository.findUserByEmail(email)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found with email " + email));
//        userRepository.delete(user);
//        return true;
//    }
//
//    //custom query
//    public UserResponseDTO findByUsername(String username) {
//        User user = userRepository.findUserByUsername(username)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found with username " + username));
//        return convertToResponseDTO(user);
//    }
//
//    public UserResponseDTO findByEmail(String email) {
//        User user = userRepository.findUserByEmail(email)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found with email " + email));
//        return convertToResponseDTO(user);
//    }
//
//    private UserResponseDTO convertToResponseDTO(User user) {
//        UserResponseDTO userResponseDTO = new UserResponseDTO();
//        BeanUtils.copyProperties(user, userResponseDTO);
//        return userResponseDTO;
//    }
//
//
//
//}
//
//




package com.example.helphub.service;

import com.example.helphub.dto.UserDTO;
import com.example.helphub.exception.ResourceNotFoundException;
import com.example.helphub.entity.User;
import com.example.helphub.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        return convertToDTO(user);
    }

    public UserDTO findByUsername(String username) {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username " + username));
        return convertToDTO(user);
    }

    public UserDTO findByEmail(String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email " + email));
        return convertToDTO(user);
    }

    public UserDTO save(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    public UserDTO update(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        BeanUtils.copyProperties(userDTO, user, "id");
        User updatedUser = userRepository.save(user);
        return convertToDTO(updatedUser);
    }

    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        userRepository.delete(user);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        userDTO.setPassword(null);
        userDTO.setEmail(null);
        return userDTO;
    }
}
