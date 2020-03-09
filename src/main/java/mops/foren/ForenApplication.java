package mops.foren;

import mops.foren.services.DomainService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;

import static org.springframework.context.annotation.FilterType.ANNOTATION;

@SpringBootApplication
@ComponentScan(
        includeFilters = {
                @Filter(type = ANNOTATION, classes = DomainService.class)
        }
)
public class ForenApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForenApplication.class, args);
    }

}
