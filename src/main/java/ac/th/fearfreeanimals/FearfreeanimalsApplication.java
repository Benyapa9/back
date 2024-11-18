package ac.th.fearfreeanimals;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@ComponentScan(basePackages = "ac.th.fearfreeanimals")
@EntityScan(basePackages = "ac.th.fearfreeanimals.entity")
@EnableJpaRepositories(basePackages = "ac.th.fearfreeanimals.repository")
public class FearfreeanimalsApplication {
    public static void main(String[] args) {
        SpringApplication.run(FearfreeanimalsApplication.class, args);
    }
}
