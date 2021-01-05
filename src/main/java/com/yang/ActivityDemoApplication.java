package com.yang;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * explain：启动类
 *
 * @author yang
 * @date 2021/1/1
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ActivityDemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ActivityDemoApplication.class, args);
	}

}
