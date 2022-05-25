package ru.hogwarts.school;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
@OpenAPIDefinition
public class DemoApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .profiles("test")
                .sources(DemoApplication.class)
                .run(args);
    }

}
