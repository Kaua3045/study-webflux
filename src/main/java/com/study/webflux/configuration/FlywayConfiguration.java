package com.study.webflux.configuration;

//import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableConfigurationProperties({ R2dbcProperties.class, FlywayProperties.class })
public class FlywayConfiguration {

//    @Bean(initMethod = "migrate")
//    public Flyway flywayInitializer(FlywayProperties properties, R2dbcProperties r2dbcProperties) {
//        return Flyway.configure()
//                .dataSource(properties.getUrl(), r2dbcProperties.getUsername(), r2dbcProperties.getPassword())
//                .locations(properties.getLocations().toArray(String[]::new))
//                .baselineOnMigrate(true)
//                .load();
//    }
}
