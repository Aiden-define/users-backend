package ctgu.yao.usersbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("ctgu.yao.usersbackend.mapper")
public class UsersBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsersBackendApplication.class, args);
    }

}
