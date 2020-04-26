package top.music.musicservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("top.music.musicservice.dao")
@SpringBootApplication
@EnableTransactionManagement   //开启事务
public class MusicserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicserviceApplication.class, args);
    }

}
