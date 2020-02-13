package com.reman.medical;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableEurekaClient
public class MedicalWebSocketApp
{
    public static void main( String[] args )
    {

        SpringApplication.run(MedicalWebSocketApp.class,args);
    }
}
