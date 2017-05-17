package lin.core.user.vo;

 

import java.io.Serializable;

 

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

@ApiModel(value="返回用户列表")

public class RUserListVo implements Serializable {

 

 

         /**

         *

          */

         private static final long serialVersionUID = 5513511336188690794L;

         @ApiModelProperty(value="用户ID")

         private Long id;

         @ApiModelProperty(value="用户账户")

         private String account;

         @ApiModelProperty(value="用户中文名称")

         private String nameCn;

         @ApiModelProperty(value="用户头像")

         private String photoUrl;

 

 

}
