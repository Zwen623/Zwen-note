package com.zwen.ipet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.zwen.ipet.common.config.DruidDataSourceConfig;

/**
 * 系统启动类
 * @author zwen
 *
 */
@SpringBootApplication
@EnableScheduling
@ServletComponentScan
@Import(DruidDataSourceConfig.class)
public class IpetSpringApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(IpetSpringApplication.class, args);
	}
	
}
