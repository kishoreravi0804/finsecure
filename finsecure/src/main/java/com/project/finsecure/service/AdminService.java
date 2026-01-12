package com.project.finsecure.service;




import com.project.finsecure.entity.Users;
import com.project.finsecure.repository.UserRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final UserRepo userRepository;
    private final BCryptPasswordEncoder encoder;

    public AdminService(UserRepo userRepository,
                        BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public Users createUser(String email, String tempPassword) {

        Users user = new Users();
        user.setEmail(email);
        user.setPassword(encoder.encode(tempPassword));
        user.setRole("user");
        user.setFirstLogin(true);
        user.setActive(true);

        return userRepository.save(user);
    }
}
