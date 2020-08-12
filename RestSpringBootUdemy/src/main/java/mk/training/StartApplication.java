package mk.training;

import mk.training.config.FileStorageConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageConfig.class
})
public class StartApplication {

  public static void main(String[] args) {
    SpringApplication.run(StartApplication.class, args);

//    BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
//    String token = bc.encode("123");
//    System.out.println("token: " + token);
  }
}
