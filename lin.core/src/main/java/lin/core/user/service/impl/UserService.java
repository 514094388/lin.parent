package lin.core.user.service.imp;

 

import java.util.List;

 

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;

 

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

 

import lin.core.constants.UserConstants;

import lin.core.user.dao.IUserDao;

import lin.core.user.service.IUserService;

import lin.core.user.vo.RUserListVo;

import lin.core.user.vo.RUserTimeLineVo;

import lin.core.user.vo.UserInfoVo;

 

@Service("userService")

public class UserService implements IUserService {

 

//      @Autowired

//      private  HttpServletRequest request;

        

         @Autowired

         private IUserDao userDao;

        

         @Autowired

         private  HttpServletRequest request;

        

         public UserInfoVo getSessionUserInfoVo(){

                   HttpSession session = request.getSession();

                   return (UserInfoVo)session.getAttribute(UserConstants.USERINFOVO);

         }

        

         public UserInfoVo getUserInfoVo(UserInfoVo userInfoVo){

                   return userDao.getUserInfoVo(userInfoVo);

         }

 

         /**

         * 保存用户信息

         */

         @Override

         public void saveUserInfoVo(UserInfoVo userInfoVo) {

                  

         }

        

         /**

         * 编辑用户信息

         */

         public void editUserInfoVo(UserInfoVo userInfoVo){

                  

         }

 

         @Override

         public List<RUserListVo> getAllUserList(String userStr) {

                   // TODO Auto-generated method stub

                   return userDao.getAllUserList(userStr);

         }

 

        

         public List<String> getUserTimeLineFix(Long userId){

                   return userDao.getUserTimeLineFix(userId);

         }

        

         public List<RUserTimeLineVo> getUserTimeLineByYear(Long userId,Long year){

                   return userDao.getUserTimeLineByYear(userId,year);

         }

 

         @Override

         public void update(UserInfoVo userInfoVo) {

                   // TODO Auto-generated method stub

                   userDao.update(userInfoVo);

         }

}
