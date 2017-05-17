package lin.core.sso.servlet;

 

import java.io.IOException;

import java.io.PrintWriter;

import java.util.Arrays;

 

import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

 

import com.huawei.it.sso.filter.util.SsoConstants;

import com.huawei.it.support.usermanage.helper.UserInfoBean;

 

 

 

@WebServlet(name="LogonServlet",urlPatterns="/LogonServlet")

public class LogonServlet extends HttpServlet {

         private static final long serialVersionUID = 174975246087680185L;

 

         public void doGet(HttpServletRequest request, HttpServletResponse response)

                            throws ServletException, IOException {

                   doPost(request, response);

         }

 

         public void doPost(HttpServletRequest request, HttpServletResponse response)

                            throws ServletException, IOException {

        

                   PrintWriter print = response.getWriter();

        

                   HttpSession session = request.getSession();

                   UserInfoBean uib = (UserInfoBean) session

                                     .getAttribute(SsoConstants.SESSION_USER_INFO_KEY);

                   if (uib == null) {

                            print.write("<html><body>");

                            print.write("User Not logon");

                            print.write("<br><a href='/ssodemo/login'>login</a>");

                            print.write("</body></html>");

                   } else {

                            String tmp;

                            print.write("<html><body>");

                            print.write("User " + uib.getUid() + " logon!");

 

                            print.write("<br>uib.getUid():");

                            print.write(uib.getUid());

                           

                            print.write("<br>uib.getDn():");

                            tmp = uib.getDn();

                            if (tmp != null) {

                                     print.write(tmp);

                            }

                           

                            print.write("<br>uib.getSn():");

                            tmp = uib.getSn();

                            if (tmp != null) {

                                     print.write(tmp);

                            }

                           

                            print.write("<br>uib.getEmployeeType():");

                            tmp = uib.getEmployeeType();

                            if (tmp != null) {

                                     print.write(tmp);

                            }

                           

                            print.write("<br>uib.getEmployeeNumber():");

                            tmp = uib.getEmployeeNumber();

                            if (tmp != null) {

                                     print.write(tmp);

                            }

                           

                            print.write("<br>uib.getEmail():");

                            tmp = Arrays.deepToString(uib.getEmail());

                            if (tmp != null) {

                                     print.write(tmp);

                            }

                           

                            print.write("<br>uib.getDisplayName(UserInfoBean.KEY_LANG_CN):");

                            tmp = uib.getDisplayName(UserInfoBean.KEY_LANG_CN);

                            if (tmp != null) {

                                     print.write(tmp);

                            }

                           

                            print.write("<br>uib.getDisplayName(UserInfoBean.KEY_LANG_EN):");

                            tmp = uib.getDisplayName(UserInfoBean.KEY_LANG_EN);

                            if (tmp != null) {

                                     print.write(tmp);

                            }

                           

                            print.write("<br>uib.getGivenName():");

                            tmp = uib.getGivenName();

                            if (tmp != null) {

                                     print.write(tmp);

                            }

                           

                            print.write("<br>uiBean.getDepartmentNumber():");

                            tmp = uib.getDepartmentNumber();

                            if (tmp != null) {

                                     print.write(tmp);

                            }

                           

                            print.write("<br>uiBean.getDepartmentName(UserInfoBean.KEY_LANG_CN)):");

                            tmp = uib.getDepartmentName(UserInfoBean.KEY_LANG_CN);

                            if (tmp != null) {

                                     print.write(tmp);

                            }

                           

                            print.write("<br>uib.getDepartmentName(UserInfoBean.KEY_LANG_EN):");

                            tmp = uib.getDepartmentName(UserInfoBean.KEY_LANG_EN);

                            if (tmp != null) {

                                     print.write(tmp);

                            }

 

                            print.write("<br><a href='/ssodemo/logout'>logout</a>");

                            print.write("</body></html>");

                   }

 

                   print.flush();

                   print.close();

         }

}
