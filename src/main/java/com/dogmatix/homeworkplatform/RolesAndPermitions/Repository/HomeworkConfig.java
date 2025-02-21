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
@EnableJpaRepositories(
    basePackages = "com.dogmatix.homeworkplatform.RolesAndPermitions.Repository.HomeworkRepository",
    entityManagerFactoryRef = "homeworkdbEntityManagerFactory",
    transactionManagerRef = "homeworkdbTransactionManager"
)
public class HomeworkConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.homeworkdb")
    public DataSource homeworkdbDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean homeworkdbEntityManagerFactory(
        @Qualifier("homeworkdbDataSource") DataSource dataSource
    ) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.dogmatix.homeworkplatform.RolesAndPermitions.Model");
        em.setJpaVendorAdapter(new org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter());
        return em;
    }

    @Bean
    public PlatformTransactionManager homeworkdbTransactionManager(
        @Qualifier("homeworkdbEntityManagerFactory") EntityManagerFactory entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
