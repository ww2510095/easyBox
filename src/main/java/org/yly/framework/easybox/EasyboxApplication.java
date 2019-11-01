package org.yly.framework.easybox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yly.framework.easybox.init.EasyBoxScan;

@SpringBootApplication
@EasyBoxScan
public class EasyboxApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyboxApplication.class, args);
        System.out.println("===========");
    }


}
