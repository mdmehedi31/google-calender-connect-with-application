package com.googlecalender.service;

import com.googlecalender.repository.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService implements IUserService {

    private IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String createUser(String email, String username) {
        return "";
    }

    @Override
    public String getRefreshTokenByUserId(Long userId) {
        return "";
    }

    @Override
    public Map<String, String> getUserGoogleCalendarTokenByUserId(Long userId) {
        return Map.of();
    }

    @Override
    public String updateUserGoogleCalendarTokenByUserId(Long userId, String token, Long expiresIn) {
        return "";
    }
}
