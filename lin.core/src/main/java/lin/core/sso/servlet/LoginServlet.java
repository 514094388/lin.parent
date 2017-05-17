package lin.core.sso.servlet;

 

import java.io.IOException;

 

import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

 

import com.huawei.it.sso.filter.util.SsoUtil;

 

 

@WebServlet(name="LoginServlet",urlPatterns="/login")

public class LoginServlet extends HttpServlet {

         private static final long serialVersionUID = 174975246087680185L;

 

         public void doGet(HttpServletRequest request, HttpServletResponse response)

                            throws ServletException, IOException {

                   doPost(request, response);

         }

 

         public void doPost(HttpServletRequest request, HttpServletResponse response)

                            throws ServletException, IOException {

                   try {

                            SsoUtil.loginAndRedirect2AppRoot(request, response);

                   } catch (Exception e) {

                            e.printStackTrace();

                   }

         }

 

 

}
