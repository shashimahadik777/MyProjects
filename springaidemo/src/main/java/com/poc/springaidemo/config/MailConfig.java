package com.poc.springaidemo.config;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Configuration
@PropertySource("classpath:mail.properties")
@ConfigurationProperties(prefix = "mail")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class MailConfig {
    //@NotNull
    private String to;
    //@NotNull
    private String from;
    //@Max(value = 40)
    //@Min(value = 20)
    private Integer age;

    //@NotNull
    private String firstname;  //loose binding
    //@NotNull
    private String lastname; //loose binding
    //@NotNull
    private String middlename; //loose binding

    private String[] cc;
    private List<String> bcc;

    @Valid
    private Credential credential = new Credential();

    @Data
    public class Credential {
        private String username;
        //@Size(min = 4, max = 8)
        private String password;
    }
}
