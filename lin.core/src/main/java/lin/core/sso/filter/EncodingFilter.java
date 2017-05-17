package lin.core.sso.filter;

 

import java.io.IOException;

 

import javax.servlet.Filter;

import javax.servlet.FilterChain;

import javax.servlet.FilterConfig;

import javax.servlet.ServletException;

import javax.servlet.ServletRequest;

import javax.servlet.ServletResponse;

import javax.servlet.http.HttpServletRequest;

 

import com.huawei.it.sso.filter.util.SsoFilterConstants;

import com.huawei.it.sso.filter.util.SsoFilterUtil;

 

/**

 

*

 

* EncodingFilter过滤器主要是统一传输内容编码

 

* @WebFilter将一个实现了javax.servlet.Filter接口的类定义为过滤器

 

* 属性filterName声明过滤器的名称,可选

 

* 属性urlPatterns指定要过滤的URL模式,也可使用属性value来声明.(指定要过滤的URL模式是必选属性)

 

*/

 

//@WebFilter(filterName="encodingFilter",urlPatterns="/*",initParams = {

//        @WebInitParam(name = "encode", value = "utf-8")

//    }

//)

public class EncodingFilter implements Filter {

 

         private String encode = null;

 

         public void init(FilterConfig filterConfig) throws ServletException {

                   encode = filterConfig.getInitParameter("encode");

         }

 

         public void doFilter(ServletRequest req, ServletResponse resp,

                            FilterChain chain) throws IOException, ServletException {

                   /*String ubStr =*/ SsoFilterUtil.getCookieValueByName(

                                     (HttpServletRequest) req,

                                     SsoFilterConstants.COOKIENAME_HWSSO_TEST);

                  

                   if (null == encode) {

                            req.setCharacterEncoding("utf-8");

                            resp.setCharacterEncoding("utf-8");

                   } else {

                            req.setCharacterEncoding(encode);

                            resp.setCharacterEncoding(encode);

                   }

                   try {

                            chain.doFilter(req, resp);

                   } catch (Exception e) {

                            e.printStackTrace();

                   }

         }

 

         public void destroy() {

         }

}
