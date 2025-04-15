package com.xpert.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_device_login_info")
public class UserDeviceLoginInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ip_address")
    private String ipAddress;  // PostgreSQL INET → Java String

    @Column(name = "device_type", length = 20)
    private String deviceType;

    @Column(name = "os", length = 15)
    private String os;

    @Column(name = "browser", length = 30)
    private String browser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;  // Refers to the 'Users' entity

    // Constructors
    public UserDeviceLoginInfo() {
    }

    public UserDeviceLoginInfo(String ipAddress, String deviceType, String os, String browser, Users user) {
        this.ipAddress = ipAddress;
        this.deviceType = deviceType;
        this.os = os;
        this.browser = browser;
        this.user = user;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
