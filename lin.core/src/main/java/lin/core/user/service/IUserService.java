package lin.core.user.service;

 

import java.util.List;

 

import lin.core.user.vo.RUserListVo;

import lin.core.user.vo.RUserTimeLineVo;

import lin.core.user.vo.UserInfoVo;

 

 

/**

* 用户中心操作逻辑处理类接口

* @author lWX395320

*

*/

 

public interface IUserService{

        

        

         public UserInfoVo getUserInfoVo(UserInfoVo userInfoVo);

 

         public void saveUserInfoVo(UserInfoVo userInfoVo);

        

         public void editUserInfoVo(UserInfoVo userInfoVo);

 

         public List<RUserListVo> getAllUserList(String userStr);

        

         public List<String> getUserTimeLineFix(Long userId);

        

         public List<RUserTimeLineVo> getUserTimeLineByYear(Long userId,Long year);

 

         public UserInfoVo getSessionUserInfoVo();

 

         public void update(UserInfoVo userInfoVo);

        

}
