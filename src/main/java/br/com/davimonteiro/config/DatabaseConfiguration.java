package br.com.davimonteiro.config;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("br.com.davimonteiro.repository")
public class DatabaseConfiguration {
	
	@Bean(destroyMethod = "close")
	public DataSource dataSource(DataSourceProperties properties) {
		HikariConfig config = new HikariConfig();
		config.setDriverClassName(properties.getDriverClassName());
		config.setJdbcUrl(properties.getUrl());
		config.setUsername(properties.getUsername());
		config.setPassword(properties.getPassword());
		return new HikariDataSource(config );
	}
	
	@Bean(initMethod = "migrate")
	public Flyway flyway(DataSource dataSource) throws Exception {
		Flyway flyway = new Flyway();
		flyway.setLocations("classpath:db/migration");
		flyway.setDataSource(dataSource);
		return flyway;
	}
	
	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}
	
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setDatabase(Database.POSTGRESQL);
		jpaVendorAdapter.setGenerateDdl(false);
		return jpaVendorAdapter;
	}
	
	@Bean
	@DependsOn("flyway")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) throws Exception {
		LocalContainerEntityManagerFactoryBean lemfb = new LocalContainerEntityManagerFactoryBean();
		lemfb.setDataSource(dataSource);
		lemfb.setJpaVendorAdapter(jpaVendorAdapter());
		lemfb.setPackagesToScan("br.com.davimonteiro.domain");
		lemfb.setJpaProperties(hibernateProperties());
		return lemfb;
	}
	
	@Bean
	public JPAQueryFactory queryFactory(EntityManager entityManager) {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		return queryFactory;
	}
	
	private Properties hibernateProperties() {
		Properties hibernateProperties = new Properties();

		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		hibernateProperties.setProperty("hibernate.show_sql", "true");
		hibernateProperties.setProperty("hibernate.format_sql", "true");
		hibernateProperties.setProperty("hibernate.use_sql_comments", "true");
		hibernateProperties.setProperty("hibernate.jdbc.fetch_size", "25");
		hibernateProperties.setProperty("hibernate.jdbc.batch_size", "50");
		hibernateProperties.setProperty("hibernate.temp.use_jdbc_metadata_defaults", "false");
		
		return hibernateProperties;
	}
	
}
