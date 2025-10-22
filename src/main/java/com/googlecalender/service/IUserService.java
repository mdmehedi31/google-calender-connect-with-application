package com.googlecalender.service;

import java.util.Map;

public interface IUserService {

    public String createUser(String email, String username);

    public String getRefreshTokenByUserId(Long userId);
    public Map<String, String> getUserGoogleCalendarTokenByUserId(Long userId);
    public String updateUserGoogleCalendarTokenByUserId(Long userId, String token, Long expiresIn);
}
