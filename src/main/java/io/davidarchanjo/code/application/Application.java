package io.davidarchanjo.code.application;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebFluxAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@EntityScan(basePackages = "io.davidarchanjo.code.model")
@SpringBootApplication(scanBasePackages = "io.davidarchanjo.code", exclude = ErrorWebFluxAutoConfiguration.class)
@EnableR2dbcRepositories(basePackages = "io.davidarchanjo.code.repository")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ConnectionFactoryInitializer databaseInitializer(ConnectionFactory connectionFactory) {
        final ConnectionFactoryInitializer cfi = new ConnectionFactoryInitializer();
        cfi.setConnectionFactory(connectionFactory);

        final CompositeDatabasePopulator cdp = new CompositeDatabasePopulator();
        cdp.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("schema/schema.sql")));
        cdp.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("schema/data.sql")));
        cfi.setDatabasePopulator(cdp);
        return cfi;
    }

}