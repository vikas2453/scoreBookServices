package com.fun.learning.config;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfig {

    @Autowired
    DataSource dataSource;
    @Autowired
    Config config;

    @Bean
    public Flyway flyway(){
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
         flyway.setSqlMigrationPrefix("V");
            flyway.setLocations(new String[] { config.getSqlLocation() });
            flyway.setBaselineOnMigrate(true);
            // *******************flyway.clean(); ********************//// this will wipe out the DB, be careful
            flyway.repair();
          flyway.migrate();
        return  flyway;

    }

}