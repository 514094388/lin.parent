package lin.core.common;

 

import java.io.Serializable;

 

import io.swagger.annotations.ApiModel;

import io.swagger.annotations.ApiModelProperty;

import lombok.Data;

 

@Data

@ApiModel(value="PageResultVo value",description="PageResultVo description")

public class ResponseVo<T> implements Serializable{

 

         /**

         *

          */

         private static final long serialVersionUID = -3866542596310374159L;

        

         @ApiModelProperty(value="提示信息",required=true)

         private String message = "";

         @ApiModelProperty(value="执行是否成功",required=true)

         private boolean success = true;

         @ApiModelProperty(value="错误编码",required=true)

         private long code = 200;

         @ApiModelProperty(

                   value="成功实际信息，"

                            + "200:成功，"

                            + "500:后台程序异常，"

                            + "600:请求参数验证不通过",required=true)

         private T response;

        

        

}
