package com.dogmatix.homeworkplatform.RolesAndPermitions.Repository;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableJpaRepositories(
    basePackages = "com.dogmatix.homeworkplatform.RolesAndPermitions.Repository",
    excludeFilters = {
        @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = EnrollmentRepository.class),
        @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = SubmissionRepository.class),
        @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = ClassRepository.class),
        @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = HomeworkRepository.class),
    },
    entityManagerFactoryRef = "userdbEntityManagerFactory",
    transactionManagerRef = "userdbTransactionManager"
)
public class UserRepoConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.userdb")
    public DataSource userdbDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean userdbEntityManagerFactory (
        @Qualifier("userdbDataSource") DataSource dataSource
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
    public PlatformTransactionManager userdbTransactionManager(
        @Qualifier("userdbEntityManagerFactory") EntityManagerFactory entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
