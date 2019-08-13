package com.myhexin.consulAdmin.config;

import com.ecwid.consul.v1.ConsulClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsulClientConfiguration {
    private ConsulProperties consulProperties;

    public ConsulClientConfiguration(ConsulProperties consulProperties){
        this.consulProperties = consulProperties;
    }

    @Bean
    public ConsulClient consulClient(){
        ConsulClient client;
        //没有配置时默认
        if (consulProperties == null) {
            client = new ConsulClient("127.0.0.1", 8500);
        }
        else {
            client = new ConsulClient(consulProperties.getConsulIP(), consulProperties.getConsulPort());
        }
        return client;
    }

}
