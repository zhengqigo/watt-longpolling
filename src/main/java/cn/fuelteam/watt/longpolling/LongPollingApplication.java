package cn.fuelteam.watt.longpolling;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LongPollingApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(LongPollingApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}
