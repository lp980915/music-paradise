package top.music.musicservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("top.music.musicservice.dao")
@SpringBootApplication
public class MusicserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicserviceApplication.class, args);
    }

}
