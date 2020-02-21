package com.zedge.homework;

import com.zedge.homework.models.User;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.h2.H2DatabasePlugin;
import org.jdbi.v3.core.mapper.reflect.ConstructorMapper;
import org.jdbi.v3.jackson2.Jackson2Plugin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;

@SpringBootApplication
public class HomeworkApplication {
	public static void main(String[] args) {
		SpringApplication.run(HomeworkApplication.class, args);
	}

	@Bean
	public Jdbi jdbi(DataSource dataSource) {

		Jdbi jdbi = Jdbi.create(new TransactionAwareDataSourceProxy(dataSource));
		jdbi.installPlugins();
		jdbi.installPlugin(new H2DatabasePlugin());
		jdbi.installPlugin(new Jackson2Plugin());

		jdbi.registerRowMapper(User.class, ConstructorMapper.of(User.class));

		return jdbi;
	}
}
