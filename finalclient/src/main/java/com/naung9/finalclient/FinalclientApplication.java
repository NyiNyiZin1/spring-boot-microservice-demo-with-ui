package com.naung9.finalclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@SpringBootApplication
@EnableFeignClients
@EnableHypermediaSupport(type = {EnableHypermediaSupport.HypermediaType.COLLECTION_JSON, EnableHypermediaSupport.HypermediaType.HAL})
public class FinalclientApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinalclientApplication.class, args);
    }

}
