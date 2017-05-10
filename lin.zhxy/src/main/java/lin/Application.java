 package lin;

 

import org.apache.log4j.Logger;

import org.mybatis.spring.annotation.MapperScan;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.web.support.SpringBootServletInitializer;

 

 

//-Psit -Dmaven.test.skip=true

 

@SpringBootApplication

@EnableAutoConfiguration

@MapperScan("lin")

public class Application  extends SpringBootServletInitializer {

    private static Logger logger = Logger.getLogger(Application.class);

 

    public static void main(String[] args) {

       

        SpringApplication.run(Application.class, args);

        logger.info("============= SpringBoot Start Success =============");

    }

   

}

 
