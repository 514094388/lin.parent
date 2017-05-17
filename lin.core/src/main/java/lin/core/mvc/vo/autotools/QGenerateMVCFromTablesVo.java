package lin.core.mvc.vo.autotools;

 

import java.io.Serializable;

import java.util.List;

 

import io.swagger.annotations.ApiModel;

import io.swagger.annotations.ApiModelProperty;

import lombok.Data;

 

@Data

@ApiModel(value="qGenerateMVCFromTablesVoList",description="根据表自动生成MVC代码传入参数")

public class QGenerateMVCFromTablesVo implements Serializable {

 

 

         /**

         *

          */

         private static final long serialVersionUID = -8104052473655351936L;

        

         @ApiModelProperty(value = "代码保存路径，默认值：/lin/mvc")

         private String savePath = "D://workspace1/lin.parent/lin.authority/src/main";

        

         @ApiModelProperty(value = "基础包路径,默认值：lin.mvc")

         private String basePackage = "lin.authority";

        

         @ApiModelProperty(value = "需要生成代码的表名")

         private List<QTableLinkListVo> qTableLinkVoLists;

        

}
