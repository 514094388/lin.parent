package lin.core.user.controller;

 

import java.io.File;

import java.io.IOException;

import java.util.List;

 

import org.apache.commons.io.FileUtils;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;

 

import lin.core.common.AbstractControllerBase;

import lin.core.common.ResponseVo;

import lin.core.user.service.IUserService;

import lin.core.user.vo.RUserListVo;

import lin.core.user.vo.RUserTimeLineVo;

import lin.core.user.vo.UserInfoVo;

import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;

import io.swagger.annotations.ApiParam;

 

/**

* 用户中心操作发布接口类

* @author lWX395320

*

*/

@Api(value="用户模块",produces=MediaType.APPLICATION_JSON_VALUE)

@RestController

@RequestMapping("/api/foundation/user")

public class UserController extends AbstractControllerBase {

        

         @Autowired

         IUserService userService;

        

         @Value("${userPermissionFile}")

         private String userPermissionFile;

        

         /**

         * 请求当前登录用户信息

         * @return

         * {

         *   uid:用户ID,

         *   nameCn:用户中文姓名,

         *   nameEn:用户英文姓名,

         *   account:用户账号,

         *   email:用户华为邮箱,

         *   photoUrl:照片地址

         *  }

         * 

          *  请求方式:http Get

         */

         @ApiOperation("请求当前登录用户信息")

         @GetMapping("/getCurUserInfoVo")

         public ResponseVo<UserInfoVo> getCurUserInfoVo(@ApiParam(value="用户账号或名字的部分字符串",required= true)@RequestParam(value="account",required=false) String account){

 

                   UserInfoVo userInfoVo = null;

                   try{

                            if(account != null){

                                     userInfoVo = new UserInfoVo();

                                     userInfoVo.setAccount(account);

                            }else{

                                     userInfoVo =getUserInfoVo();

                            }

                            userInfoVo = userService.getUserInfoVo(userInfoVo);

        

                   }catch(Exception e){

                            e.getStackTrace();

                            return error(500,e.getMessage());

                   }

                   return response(userInfoVo);

         }

        

         @ApiOperation("更改用户信息")

         @GetMapping("/update")

         public @ApiParam("获取用户列表")ResponseVo<List<UserInfoVo>> update(

                            @ApiParam(value="用户账号或名字的部分字符串",required= true)

                            List<UserInfoVo> userInfoVoList){

                   try{

                            for(UserInfoVo userInfoVo:userInfoVoList){

                                     userService.update(userInfoVo);

                            }

                   }catch(Exception e){

                            e.getStackTrace();

                            return error(500,e.getMessage());

                   }

                   return response(userInfoVoList);

         }

        

         @ApiOperation("请求用户账户、名称列表")

         @GetMapping("/getAllUserList")

         public @ApiParam("获取用户列表")ResponseVo<List<RUserListVo>> getAllUserList(

                            @ApiParam(value="用户账号或名字的部分字符串",required= true)

                            @RequestParam("userStr") String userStr){

                   List<RUserListVo> rUserListVoList = null;

                   try{

                            rUserListVoList = userService.getAllUserList(userStr);

        

                   }catch(Exception e){

                            e.getStackTrace();

                            return error(500,e.getMessage());

                   }

                   return response(rUserListVoList);

         }

        

        

         @ApiOperation("请求用户Time line")

         @GetMapping("/getUserTimeLineFix")

         public  ResponseVo<List<String>> getUserTimeLineFix(){

                   List<String> list = null;

                   try{

                            UserInfoVo userInfoVo = getUserInfoVo();

                            list = userService.getUserTimeLineFix(userInfoVo.getId());

        

                   }catch(Exception e){

                            e.getStackTrace();

                            return error(500,e.getMessage());

                   }

                   return response(list);

         }

        

        

         @ApiOperation("请求用户getUserTimeLineByYear")

         @GetMapping("/getUserTimeLineByYear/{year}")

         public  ResponseVo<List<RUserTimeLineVo>> getUserTimeLineByYear(

                            @ApiParam(value="年份",defaultValue="2016",required= true)

                            @PathVariable Long year){

                   List<RUserTimeLineVo> rUserTimeLineVoList = null;

                   try{

                            UserInfoVo userInfoVo = getUserInfoVo();

                            rUserTimeLineVoList = userService.getUserTimeLineByYear(userInfoVo.getId(),year);

        

                   }catch(Exception e){

                            e.getStackTrace();

                            return error(500,e.getMessage());

                   }

                   return response(rUserTimeLineVoList);

         }

        

        

         @ApiOperation("请求用户登录权限")

         @GetMapping("/getUserPermission")

         public Boolean getUserPermission(@ApiParam(value="用户账号或名字的部分字符串",required= true)@RequestParam(value="account",required=false) String account) throws IOException{

        

                   File permissionFile = new File(userPermissionFile);//实时读，不缓存

                   if(!permissionFile.exists()){ //如果文件不存在，默认所有用户都有权限

                            return true;

                   }

                   UserInfoVo userInfoVo = null;

                   if(account != null){

                            userInfoVo = new UserInfoVo();

                            userInfoVo.setAccount(account);

                   }else{

                            userInfoVo =getUserInfoVo();

                   }

                  

                  

                   List<String> userList = FileUtils.readLines(permissionFile, "UTF-8");

                   for(String userAccount : userList) {

                            if(userAccount.equalsIgnoreCase(userInfoVo.getAccount())){

                                     return true;

                            }

                   }

        

                   return false;

         }

}
