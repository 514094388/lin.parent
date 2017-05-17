package lin.core.mvc.vo;

 

import java.util.Date;

 

import lin.core.common.PageVo;

 

import io.swagger.annotations.ApiModel;

import io.swagger.annotations.ApiModelProperty;

import lombok.Data;

 

 

@Data

@ApiModel(value="EUIHistory",description="EUIHistory")

public class QHistoryVo extends PageVo{

        

         /**

         *

          */

         private static final long serialVersionUID = 6837877299576852310L;

 

         @ApiModelProperty(value = "主键")

         private Long historyId;

        

         private String objectType;

        

         private Long objectId;

        

         @ApiModelProperty(value = "动作")

         private String action;

        

         private String previous;

        

         private String after;

        

        

         @ApiModelProperty(value = "创建人")

         private Long createdBy;// NUMBER Y 创建人

        

         @ApiModelProperty(value = "创建时间")

         private Date creationDate;// DATE Y 创建时间

        

         @ApiModelProperty(value = "最后更新用户")

         private Long lastUpdatedBy;// NUMBER Y 最后更新用户

        

         @ApiModelProperty(value = "最后更新时间")

         private Date lastUpdateDate;// DATE Y 最后更新时间

 

         @ApiModelProperty(value = "是否已经删除 false 表示没有删除")

    private Boolean isDelete;        //是否删除

   

}
