package com.tecule.kestrel.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class JpaConfiguration {
	/**
	 * use entityManagerFactory bean to create a transactionManager bean.
	 * 
	 * @return
	 */
	@Bean
	public PlatformTransactionManager transactionManager() {
		return new JpaTransactionManager( localContainerEntityManagerFactoryBean().getObject() );
	}

	/**
	 * use dataSource bean to create an entityManagerFactory bean.
	 * 
	 * @return
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean() {
		LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
		lef.setDataSource(this.getDataSource());
		lef.setJpaProperties(this.getJpaProperties());
		lef.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		lef.setPackagesToScan("com.tecule.kestrel.model");
		return lef;
	}

	/**
	 * using parameters to define a DataSource bean.
	 * 
	 * @return
	 */
	@Bean
	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://192.168.101.151:3306/tecule");
		dataSource.setUsername("tecule");
		dataSource.setPassword("123456");
		
		return dataSource;
	}
	
	public Properties getJpaProperties() {
		Properties jpaProperties = new Properties();
		
		jpaProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		jpaProperties.setProperty("hibernate.show_sql", "false");
		jpaProperties.setProperty("hibernate.format_sql", "true");
		jpaProperties.setProperty("hibernate.use_sql_comments", "false");
		jpaProperties.setProperty("hibernate.hbm2ddl.auto", "update");
		
		return jpaProperties;
	}
}
