package lin.core.user.dao;

 

import java.util.List;

 

import org.apache.ibatis.annotations.Param;

import org.springframework.stereotype.Component;

 

import lin.core.user.vo.RUserListVo;

import lin.core.user.vo.RUserTimeLineVo;

import lin.core.user.vo.UserInfoVo;

 

 

 

@Component

public interface IUserDao {

        

         public UserInfoVo getUserInfoVo(UserInfoVo userInfoVo);  //读取用户ID

        

         public void saveUserInfoVo(@Param("vo")UserInfoVo userInfoVo);//保存用户信息

 

         public List<RUserListVo> getAllUserList(String userStr);

        

         public List<String> getUserTimeLineFix(Long userId);

        

         public List<RUserTimeLineVo> getUserTimeLineByYear(@Param("userId") Long userId,@Param("year") Long year);

 

         public void update(@Param("vo")UserInfoVo userInfoVo);

        

}
