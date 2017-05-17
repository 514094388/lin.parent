package lin.core.sso.filter;

 

import java.io.IOException;

 

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.web.servlet.FilterRegistrationBean;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import org.springframework.core.env.Environment;

 

import lin.core.mvc.dao.IDBDao;

import lin.core.user.dao.IUserDao;

 

import com.huawei.it.sso.filter.SsoFilter;

 

//@SuppressWarnings("deprecation")

@Configuration

public class FilterConfiguration {

 

         @Autowired

         private IUserDao userDao;

        

         @Autowired

         private IDBDao dbDao;

        

         @Autowired

         Environment env;

        

         @Bean

         public FilterRegistrationBean regEncodingFilter() {

                   FilterRegistrationBean registration = new FilterRegistrationBean();

                   registration.setFilter(new EncodingFilter());

                   registration.addUrlPatterns("/*");

                   registration.addInitParameter("encode", "utf-8");

                   registration.setName("EncodingFilter");

                   registration.setOrder(1);

                   return registration;

         }

        

         @Bean

         public FilterRegistrationBean regSsoFilter() {

                   FilterRegistrationBean registration = new FilterRegistrationBean();

                   registration.setFilter(new SsoFilter());

                   registration.addUrlPatterns("/*");

                   registration.addInitParameter("serverscope", "intra");

                   registration.addInitParameter("userscope", "INTRANET_USERS");

                   registration.addInitParameter("debug", "false");

                   String activeProfiles = env.getRequiredProperty("spring.profiles.active");

                   String envString = env.getRequiredProperty("domain.name." + activeProfiles);

                   registration.addInitParameter("exclusions", "http://" + envString + "/api/wopi/files/*");

                   registration.setName("SsoFilter");

                   registration.setOrder(2);

                   return registration;

         }

        

         @Bean

         public FilterRegistrationBean regLoginFilter() throws IOException {

                   FilterRegistrationBean registration = new FilterRegistrationBean();

                   LoginFilter loginFilter = new LoginFilter(env);

                   loginFilter.setUserDao(userDao);

                   loginFilter.setDbDao(dbDao);

                   registration.setFilter(loginFilter);

                   registration.addUrlPatterns("/*");

                   registration.setName("LoginFilter");

                   registration.setOrder(3);

                   return registration;

         }

        

}
