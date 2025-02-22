package com.dogmatix.homeworkplatform.RolesAndPermitions.Repository;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
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
        classes = SubmissionRepository.class),
        @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = UserRepository.class),
        @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = GradeRepository.class),
        @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = HomeworkRepository.class),
    },
    entityManagerFactoryRef = "classesdbEntityManagerFactory",
    transactionManagerRef = "classesdbTransactionManager"
)
public class ClassRepoConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.classesdb")
    public DataSource classesdbDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean classesdbEntityManagerFactory (
        @Qualifier("classesdbDataSource") DataSource dataSource
    ) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.dogmatix.homeworkplatform.RolesAndPermitions.Model");
        em.setJpaVendorAdapter(new org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter());
        
        return em;
    }

    @Bean
    public PlatformTransactionManager classesdbTransactionManager(
        @Qualifier("classesdbEntityManagerFactory") EntityManagerFactory entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
