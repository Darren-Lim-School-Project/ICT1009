package moe.ksmz.rodentraid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RodentraidProjektApplication {

    public static void main(String[] args) {
        SpringApplication.run(RodentraidProjektApplication.class, args);
    }
}
