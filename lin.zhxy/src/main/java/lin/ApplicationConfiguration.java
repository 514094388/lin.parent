package lin;

 

import javax.servlet.MultipartConfigElement;

 

import org.apache.ibatis.session.SqlSessionFactory;

import org.apache.log4j.Logger;

import org.mybatis.spring.SqlSessionFactoryBean;

import org.mybatis.spring.mapper.MapperScannerConfigurer;

import org.springframework.boot.bind.RelaxedPropertyResolver;

import org.springframework.boot.web.servlet.MultipartConfigFactory;

import org.springframework.context.EnvironmentAware;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import org.springframework.core.env.Environment;

import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import org.springframework.transaction.PlatformTransactionManager;

import org.springframework.transaction.annotation.EnableTransactionManagement;

 

import com.alibaba.druid.pool.DruidDataSource;

 

@Configuration

@EnableTransactionManagement

public class ApplicationConfiguration implements EnvironmentAware {

 

         private static Logger logger = Logger.getLogger(ApplicationConfiguration.class);

         private RelaxedPropertyResolver propertyResolver;

 

         @Bean(destroyMethod = "close", initMethod = "init")

         public DruidDataSource dataSource() {

                   logger.info("注入druid！！！");

                   DruidDataSource datasource = new DruidDataSource();

                   datasource.setUrl(propertyResolver.getProperty("url"));

                   datasource.setDriverClassName(propertyResolver.getProperty("driver-class-name"));

                   datasource.setUsername(propertyResolver.getProperty("username"));

                   datasource.setPassword(propertyResolver.getProperty("password"));

                   //初始连接数

                   datasource.setInitialSize(Integer.valueOf(propertyResolver.getProperty("initialSize")));

                   datasource.setMinIdle(Integer.valueOf(propertyResolver.getProperty("minIdle")));

                   //最大等待秒数，单位为毫秒

                   datasource.setMaxWait(Long.valueOf(propertyResolver.getProperty("maxWait")));

                   //最大连接数据库连接数，设置为0时，表示没有限制；

                   datasource.setMaxActive(Integer.valueOf(propertyResolver.getProperty("maxActive")));

                   datasource.setMinEvictableIdleTimeMillis(

                                     Long.valueOf(propertyResolver.getProperty("minEvictableIdleTimeMillis")));

                   return datasource;

         }

        

         // 提供SqlSeesion

         @Bean

         public SqlSessionFactory sqlSessionFactoryBean() throws Exception {

                   System.out.println("App.sqlSessionFactoryBean()");

                   SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();

                   sqlSessionFactoryBean.setDataSource(dataSource());

 

                   PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

 

                   sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/lin/**/dao/*.xml"));

 

                   return sqlSessionFactoryBean.getObject();

         }

 

         // @Bean(value="multipartResolver")

         // public CommonsMultipartResolver multipartResolver() {

         // System.out.println("App.CommonsMultipartResolver()");

         // CommonsMultipartResolver commonsMultipartResolver = new

         // CommonsMultipartResolver();

         // return commonsMultipartResolver;

         // }

 

         @Bean

         public MultipartConfigElement multipartConfigElement() {

                   System.out.println("App.multipartConfigElement()");

 

                   MultipartConfigFactory factory = new MultipartConfigFactory();

                   factory.setMaxFileSize("20MB");

                   factory.setMaxRequestSize("20MB");

 

                   return factory.createMultipartConfig();

         }

 

         @Bean

         public MapperScannerConfigurer mapperScannerConfigurer() {

                   System.out.println("App.mapperScannerConfigurer()");

                   MapperScannerConfigurer mapperScannerConfig = new MapperScannerConfigurer();

                   mapperScannerConfig.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");

                   mapperScannerConfig.setBasePackage("lin");

                   return mapperScannerConfig;

         }

 

         // @Bean

         // public HttpMessageConverters fastJsonHttpMessageConverters(){

         // FastJsonHttpMessageConverter fastJsonConverter = new

         // FastJsonHttpMessageConverter();

         // FastJsonConfig fastJsonConfig = new FastJsonConfig();

         // fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);

         // fastJsonConverter.setFastJsonConfig(fastJsonConfig);

         // HttpMessageConverter<?> converter = fastJsonConverter;

         // return new HttpMessageConverters(converter);

         // }

 

         @Bean

         public PlatformTransactionManager transactionManager() {

                   System.out.println("App.transactionManager()");

                   return new DataSourceTransactionManager(dataSource());

         }

 

         @Override

         public void setEnvironment(Environment env) {

                   this.propertyResolver = new RelaxedPropertyResolver(env, "datasource."+env.getRequiredProperty("spring.profiles.active")+".");

         }

 

}
