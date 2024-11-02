package edu.mtisw.payrollbackend.services;

import edu.mtisw.payrollbackend.entities.UserEntity;
import edu.mtisw.payrollbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public UserEntity register(String rut, String name, String email, String password, String role) {
        UserEntity user = new UserEntity();
        user.setRut(rut);
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);

        UserEntity isRegistered = userRepository.findByRut(user.getRut());
        if (isRegistered != null) {
            return null;
        }
        return userRepository.save(user);
    }

    public UserEntity login(String rut, String password) {
        UserEntity user = userRepository.findByRut(rut);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}