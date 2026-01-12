package com.project.finsecure.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.finsecure.dto.ChangePasswordRequest;
import com.project.finsecure.dto.GenericResponse;
import com.project.finsecure.dto.LoginRequest;
import com.project.finsecure.dto.LoginResponse;
import com.project.finsecure.entity.Users;
import com.project.finsecure.repository.UserRepo;
import com.project.finsecure.security.JwtUtil;

@Service
public class AuthService {

    private final UserRepo userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
     private final JwtUtil jwtUtil;  

    public AuthService(UserRepo userRepository,
                       BCryptPasswordEncoder passwordEncoder,JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public LoginResponse login(LoginRequest request) {

        Optional<Users> optionalUser = userRepository.findByEmail(request.getEmail());
      

        if (optionalUser.isEmpty()) {
            return new LoginResponse(false, "Invalid email", false,null);
        }

        Users user = optionalUser.get();
       
       

        if (!user.isActive()) {
            return new LoginResponse(false, "User is inactive", false,null);
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return new LoginResponse(false, "Invalid password", false,null);
        }

        if (user.isFirstLogin()) {
            return new LoginResponse(true, "First login - password change required", true,null);
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        return new LoginResponse(true, "Login successful", false,token);
    }

    public GenericResponse changePassword(ChangePasswordRequest request) {

        Optional<Users> optionalUser = userRepository.findByEmail(request.getEmail());

        if (optionalUser.isEmpty()) {
            return new GenericResponse(false, "Invalid email");
        }

        Users user = optionalUser.get();

        if (!user.isFirstLogin()) {
            return new GenericResponse(false, "Password already updated");
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            return new GenericResponse(false, "Passwords do not match");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setFirstLogin(false);

        userRepository.save(user);

        return new GenericResponse(true, "Password updated successfully");
    }
}
