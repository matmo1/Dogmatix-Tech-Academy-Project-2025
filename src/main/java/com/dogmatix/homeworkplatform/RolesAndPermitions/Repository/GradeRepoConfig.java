package com.dogmatix.homeworkplatform.RolesAndPermitions.Repository;

import java.util.Properties;

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
    basePackages = "com.dogmatix.homeworkplatform.RolesAndPermitions.Repository.GradeRepository",
    entityManagerFactoryRef = "gradingdbEntityManagerFactory",
    transactionManagerRef = "gradingdbTransactionManager"
)
public class GradeRepoConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.gradingdb")
    public DataSource gradingdbDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean gradingdbEntityManagerFactory (
        @Qualifier("gradingdbDataSource") DataSource dataSource
    ) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.dogmatix.homeworkplatform.RolesAndPermitions.Model");
        em.setJpaVendorAdapter(new org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter());

        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MariaDBDialect");
        properties.put("hibernate.hbm2ddl.auto", "update"); // Optional: for schema updates
        em.setJpaProperties(properties);
        return em;
    }

    @Bean
    public PlatformTransactionManager gradingdbTransactionManager(
        @Qualifier("gradingdbEntityManagerFactory") EntityManagerFactory entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
