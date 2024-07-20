package com.rtemi.config;




import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource("application.properties")
public class Config {
@Bean
    @ConditionalOnProperty(name="custom.ds.condition", havingValue = "true")
    public String conditionalBean(){
        return "Conditional Bean initialized";
}
}
