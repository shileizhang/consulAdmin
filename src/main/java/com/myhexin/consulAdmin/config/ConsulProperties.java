package com.myhexin.consulAdmin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConsulProperties {
    //the default ip is 127.0.0.1
    @Value("${consul.ip:127.0.0.1}")
    private String consulIP;

    // the default port is 8500
    @Value("${consul.port:8500}")
    private int consulPort;

    public String getConsulIP() {
        return consulIP;
    }

    public void setConsulIP(String consulIP) {
        this.consulIP = consulIP;
    }

    public int getConsulPort() {
        return consulPort;
    }

    public void setConsulPort(int consulPort) {
        this.consulPort = consulPort;
    }
}