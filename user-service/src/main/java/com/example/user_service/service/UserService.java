package com.example.user_service.service;

import com.example.user_service.entity.User;
import com.example.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Auto-create if not found
    public User getOrCreateUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseGet(() -> userRepository.save(
                        User.builder()
                                .email(email)
                                .name("")
                                .phone("")
                                .address("")
                                .build()
                ));
    }

    public User updateUserByEmail(String email, User updated) {
        User existing = getOrCreateUserByEmail(email);
        existing.setName(updated.getName());
        existing.setPhone(updated.getPhone());
        existing.setAddress(updated.getAddress());
        return userRepository.save(existing);
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User createIfNotExists(String email) {
        return userRepository.findByEmail(email)
                .orElseGet(() -> userRepository.save(
                        User.builder().email(email).build()
                ));
    }

    public Optional<User> getById(Long id) {
       return userRepository.findById(id);
    }
}


//import org.springframework.stereotype.Service;
//
//import com.example.user_service.entity.User;
//import com.example.user_service.repository.UserRepository;
//import com.example.user_service.dto.UserRequest;
//
//import lombok.RequiredArgsConstructor;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class UserService {
//
//    private final UserRepository userRepository;
//
////    public User create(String email, UserRequest req) {
////        User user = userRepository.findByEmail(email).orElse(new User());
//////        user.setEmail(email);
//////        user.setName(req.getName());
//////        user.setPhone(req.getPhone());
//////        user.setAddress(req.getAddress());
////return userRepository.save(user);
////    }
//
//    public User update(String email, UserRequest req) {
//        User user = userRepository.findByEmail(email).orElse(new User());
//        user.setEmail(email);
//        user.setName(req.getName());
//        user.setPhone(req.getPhone());
//        user.setAddress(req.getAddress());
//        return userRepository.save(user);
//    }
//
//    public Optional<User> getByEmail(String email) {
//        return userRepository.findByEmail(email);
//    }
//
//    public List<User> getAll() {
//        return userRepository.findAll();
//    }
//
//    public Optional<User> getById(Long id) {
//        return userRepository.findById(id);
//    }
//}