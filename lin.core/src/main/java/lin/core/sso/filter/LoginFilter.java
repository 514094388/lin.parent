package lin.core.sso.filter;

 

 

import java.io.File;

import java.io.FileInputStream;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import java.util.Properties;

 

import javax.servlet.Filter;

import javax.servlet.FilterChain;

import javax.servlet.FilterConfig;

import javax.servlet.ServletException;

import javax.servlet.ServletRequest;

import javax.servlet.ServletResponse;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

 

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.env.Environment;

import org.thymeleaf.util.ArrayUtils;

 

import com.alibaba.fastjson.JSON;

import lin.core.constants.UserConstants;

import lin.core.mvc.dao.IDBDao;

import lin.core.user.dao.IUserDao;

import lin.core.user.vo.UserInfoVo;

 

import com.huawei.it.sso.filter.util.SsoConstants;

import com.huawei.it.sso.filter.util.SsoUtil;

import com.huawei.it.support.usermanage.helper.UserInfoBean;

 

/**

*

 *

 *

 * LoginFilter过滤器实现用户未登录自动跳转登录页面

*

 * @WebFilter将一个实现了javax.servlet.Filter接口的类定义为过滤器

*

 *                                                                                                          属性filterName声明过滤器的名称,可选

*

 *                                                属性urlPatterns指定要过滤的URL模式,也可使用属性value来声明.(指定要过滤的URL模式是必选属性)

*

 */

 

// @WebFilter(filterName="LoginFilter",urlPatterns="/*")

public class LoginFilter implements Filter {

 

         @Autowired

         public LoginFilter(Environment env) throws IOException {

                   configFilePath = env.getRequiredProperty("wopiConfig");

                   activeProfiles = env.getRequiredProperty("spring.profiles.active");

         }

        

         private final String activeProfiles;

         private final String configFilePath;

         private static Properties wopiProperties;

         private static long propFileLastModified = -1;

        

         private IDBDao dbDao;

        

         public IDBDao getDbDao() {

                   return dbDao;

         }

 

         public void setDbDao(IDBDao dbDao) {

                   this.dbDao = dbDao;

         }

 

         private IUserDao userDao;     //用户信息操作类

        

         public IUserDao getUserDao() {

                   return userDao;

         }

 

         public void setUserDao(IUserDao userDao) {

                   this.userDao = userDao;

         }

 

         public void init(FilterConfig fConfig) throws ServletException {

         }

 

         public void destroy() {

 

         }

 

         public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)

                            throws IOException, ServletException {

                   HttpServletRequest reqs = (HttpServletRequest) request;

                   String ip = reqs.getHeader("x-forwarded-for");

                   if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

                            ip = reqs.getHeader("Proxy-Client-IP");

                   }

                   if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

                            ip = reqs.getHeader("WL-Proxy-Client-IP");

                   }

                   if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

                            ip = request.getRemoteAddr();

                   }

                  

                   File propFile = new File(configFilePath);

                   if(wopiProperties == null || propFile.lastModified() > propFileLastModified) {

                            propFileLastModified = propFile.lastModified();

                            wopiProperties = new Properties();

                            InputStream in = new FileInputStream(propFile);

                            InputStreamReader isr = new InputStreamReader(in, "UTF-8");

                            wopiProperties.load(isr);

                   }

                  

                   String profiles = activeProfiles;

                  

                   if(profiles==null || profiles.trim().length() == 0){

                            profiles = "pro";

                   }

                  

                   String wopiHost = wopiProperties.getProperty("wopi.host." + profiles);

                   if(wopiHost != null) {

                            String[] wopiHosts = wopiHost.split(";");

//                         System.out.println("propsIP:" + wopiHost + " ------ currentIP:" + ip);

                            if(ArrayUtils.contains(wopiHosts, ip) && reqs.getRequestURL().toString().contains("/wopi/")){

                                     System.out.println("The host has a right to vist me!");

                                     chain.doFilter(request, response);

                                     return;

                            }

                   }

 

                   HttpSession session = ((HttpServletRequest) request).getSession();

                   UserInfoBean uiBean = (UserInfoBean) session.getAttribute(SsoConstants.SESSION_USER_INFO_KEY);

//               System.out.println(this.getClass().getName() + ":" + SsoConstants.SESSION_USER_INFO_KEY);

                   if (uiBean == null) {

                            try {

                                    

//                                  JSONObject json = new JSONObject();

//                                  json.put("success", false);

//                                  json.put("errCode", 1);

//                                  json.put("msg", "user not login: dev: https://login-beta.huawei.com/login/?redirect=http%3A%2F%2Flocalhost.huawei.com%3A8080%2Fswagger-ui.html&lang=en&msg=1&v=V0.1");

//                                  ServletOutputStream out = response.getOutputStream();

//                                  out.write(json.toString().getBytes());

                                    

                                     SsoUtil.loginAndRedirect2AppCurrentURL((HttpServletRequest) request, (HttpServletResponse) response);

                                    

                            } catch (Exception e) {

                                    

                            }

                            return;

                   } else {

                            UserInfoVo userInfoVo = (UserInfoVo) session.getAttribute(UserConstants.USERINFOVO);

                            if (userInfoVo == null) {

                                     userInfoVo= new UserInfoVo();

                                     userInfoVo.setAccount(uiBean.getUid().toLowerCase());

                                     userInfoVo = userDao.getUserInfoVo(userInfoVo);

                                     if(userInfoVo == null ){

                                               userInfoVo = new UserInfoVo();

                                               Long key = dbDao.generateKey();

                                               userInfoVo.setId(key);

                                               userInfoVo.setAccount(uiBean.getUid());

                                               userInfoVo.setEmail(JSON.toJSONString(uiBean.getEmail()));

                                               userInfoVo.setEmpNo(uiBean.getEmployeeNumber());

                                               userInfoVo.setNameCn(uiBean.getDisplayName(UserInfoBean.KEY_LANG_CN));

                                               userInfoVo.setNameEn(uiBean.getDisplayName(UserInfoBean.KEY_LANG_EN));

                                               userDao.saveUserInfoVo(userInfoVo);

                                     }

                                     session.setAttribute(UserConstants.USERINFOVO, userInfoVo);

                            }

                            chain.doFilter(request, response);

                   }

         }

 

}
