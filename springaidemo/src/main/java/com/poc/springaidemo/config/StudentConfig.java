package com.poc.springaidemo.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
//@PropertySource("classpath:student.properties")
//@PropertySource("file:\\C:\\Training\\student1.properties")
@PropertySources({
        @PropertySource("classpath:student.properties"),
        //@PropertySource("file:\\C:\\Training\\student1.properties")
})
@ConfigurationProperties(prefix = "student")
@Data  //@Getter+@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentConfig {
    private String name;
    private String address;
    private Integer age;
    private String email;
    private String course;
}
