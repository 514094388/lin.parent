package lin.core.mvc.vo;

 

import java.util.Date;

 

import io.swagger.annotations.ApiModel;

import io.swagger.annotations.ApiModelProperty;

import lombok.Data;

 

 

@Data

@ApiModel(value="HistoryVo",description="HistoryVo")

public class HistoryVo {

        

         @ApiModelProperty(value = "主键")

         private Long historyId;

        

         private String objectType;

        

         private Long objectId;

        

         @ApiModelProperty(value = "动作")

         private String action;

        

         private String previous;

        

         private String after;

        

//      EUIHistory(){

//      }

//     

//      public EUIHistory(String action,String objectType,Long objectId,Long createdBy){

//               this.action = action;

//               this.objectType = objectType;

//               this.objectId = objectId;

//               this.createdBy = createdBy;

//      }

 

 

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
