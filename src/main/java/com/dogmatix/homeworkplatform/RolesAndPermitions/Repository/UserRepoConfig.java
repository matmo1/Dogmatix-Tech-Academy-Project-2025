package com.dogmatix.homeworkplatform.RolesAndPermitions.Repository;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableJpaRepositories(basePackages = "com.dogmatix.homeworkplatform.RolesAndPermitions.Repository",
 entityManagerFactoryRef = "userEntityManagerFactory",
 transactionManagerRef = "userTransactionManager")
public class UserRepoConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.userDB")
    public DataSource homeworkDBDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean userDBEntityManagerFactoryBean(
        @Qualifier("userDBDataSource") DataSource dataSource
    ) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.dogmatix.userDB.model");
        return em;
    }

    @Bean
    public PlatformTransactionManager homeworkDBTransactionManager(
        @Qualifier("userDBEntityManagerFactory") EntityManagerFactory entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
