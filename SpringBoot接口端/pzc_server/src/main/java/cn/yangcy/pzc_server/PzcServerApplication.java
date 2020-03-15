package cn.yangcy.pzc_server;

import cn.yangcy.pzc_server.mapper.InformationMapper;
import cn.yangcy.pzc_server.mapper.UserMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
//@MapperScan(basePackageClasses = {UserMapper.class, InformationMapper.class})
public class PzcServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PzcServerApplication.class, args);
    }

}
