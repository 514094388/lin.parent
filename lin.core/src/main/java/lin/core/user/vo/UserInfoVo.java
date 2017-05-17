package lin.core.user.vo;

 

import java.io.Serializable;

import java.util.Date;

 

import io.swagger.annotations.ApiModel;

import io.swagger.annotations.ApiModelProperty;

import lombok.Data;

 

/**

* 用户信息bean

*

 * @author lWX395320

*

*/

 

@Data

@ApiModel(value="返回用户信息")

public class UserInfoVo implements Serializable {

 

         /**

         *

          */

         private static final long serialVersionUID = -3204553378139946379L;

         @ApiModelProperty(value="用户ID")

         private Long id;

         @ApiModelProperty(value="用户账户")

         private String account;

         @ApiModelProperty(value="用户中文名称")

         private String nameCn;

         @ApiModelProperty(value="用户邮箱")

         private String email;

         @ApiModelProperty(value="用户创建时间")

         private Date createdate;

         @ApiModelProperty(value="用户是否锁定 is the user locked out")

         private Integer isLockedOut;

         @ApiModelProperty(value="是否开启新手引导 is user hint enabled")

         private Integer isHintEnabled;

         @ApiModelProperty(value="最近登录 last login date")

         private Date lastLoginDate;

         @ApiModelProperty(value="错误登录次数，登录成功后会重置为0 count of incorrect login attempts, will be set to 0 after any succesful login")

         private Long incorrectLoginAttempt;

         @ApiModelProperty(value="工号，可选")

         private String empNo;

         @ApiModelProperty(value="中文名字/昵称 name/nickname")

         private String nameEn;

         @ApiModelProperty(value="照片地址")

         private String photoUrl;

         @ApiModelProperty(value="JOB_TITLE")

         private String jobTitle;

         @ApiModelProperty(value="JOB_DEPARTMENT")

         private String jobDepartment;

         @ApiModelProperty(value="REMARKS")

         private String remarks;

         @ApiModelProperty(value="MAX_SCREEN")

         private Long maxScreen;

         @ApiModelProperty(value="启用权限套，保存权限套id，以‘,’分隔")

         private Long openPrivacySet;

}
