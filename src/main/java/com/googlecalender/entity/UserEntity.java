package com.googlecalender.entity;


import jakarta.persistence.*;

@Entity(name = "tb_user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email")
    private String email;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "google_refresh_token")
    private String googleRefreshToken;

    @Column(name = "google_access_token")
    private String googleAccessToken;

    @Column(name = "google_token_expired_at")
    private Integer googleTokenExpiredAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGoogleRefreshToken() {
        return googleRefreshToken;
    }

    public void setGoogleRefreshToken(String googleRefreshToken) {
        this.googleRefreshToken = googleRefreshToken;
    }

    public String getGoogleAccessToken() {
        return googleAccessToken;
    }

    public void setGoogleAccessToken(String googleAccessToken) {
        this.googleAccessToken = googleAccessToken;
    }

    public Integer getGoogleTokenExpiredAt() {
        return googleTokenExpiredAt;
    }

    public void setGoogleTokenExpiredAt(Integer googleTokenExpiredAt) {
        this.googleTokenExpiredAt = googleTokenExpiredAt;
    }
}
