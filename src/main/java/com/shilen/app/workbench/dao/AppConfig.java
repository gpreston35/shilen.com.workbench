package com.shilen.app.workbench.dao;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;


@Configuration
@MapperScan("com.shilen.app.workbench.dao")
public class AppConfig {
	
 
	@Bean
    public DataSource getDataSource() {
       
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
       
	   dataSource.setUrl("jdbc:mysql://localhost:3306/operations?useUnicode=true&useJDBCCompliantTimezoneShift=trueode=false&serverTimezone=America/Chicago&allowMultiQueries=true");
	//	dataSource.setUrl("jdbc:mysql://10.1.1.8:3306/operations?useUnicode=true&useJDBCCompliantTimezoneShift=trueode=false&serverTimezone=America/Chicago&allowMultiQueries=true");
	dataSource.setUsername("root");
		dataSource.setPassword("notCliff");
	//	dataSource.setUsername("greg");
//	 	dataSource.setPassword("Bl33dBlu3");
	 	
		return dataSource;
   }
	
   @Bean
   public DataSourceTransactionManager transactionManager() {
       return new DataSourceTransactionManager(getDataSource());
   }
   
   @Bean
   public SqlSessionFactory sqlSessionFactory() throws Exception {
      SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
      sessionFactory.setDataSource(getDataSource());
      return sessionFactory.getObject();
   }

}

