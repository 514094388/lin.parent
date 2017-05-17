package lin.core.mvc.vo.autotools;

 

import java.io.Serializable;

 

import io.swagger.annotations.ApiModel;

import io.swagger.annotations.ApiModelProperty;

import lombok.Data;

 

@Data

@ApiModel(value="qTableLinkVo",

         description="指定关联表，"

                            + "table属性指定的表为主表，只能有一个，如果指定了多个，则取第一个为主表，其他的视为内关联表。"

                            + "示例：[{\"table\":\"EUI_TASK t\",\"on\":\"t.is_delete = 0\"},"

                            + "{\"leftJoin\":\"EUI_TASK_TAG_LINK t1\",\"on\":\"t.id = t1.task_id\"},"

                            + "{\"leftJoin\":\"EUI_TAG t2\",\"on\":\"t1.tag_id = t2.id\"}]")

public class QTableLinkVo implements Serializable  {

        

         /**

         *

          */

         private static final long serialVersionUID = 2539409433140437794L;

 

         //表名

         private String tableName;

        

         //表名

         private String joinType;

        

         //表别名

         private String alias;

        

         @ApiModelProperty(value = "需要生成代码的主表名")

         private String table;

        

         @ApiModelProperty(value = "左关联表")

         private String leftJoin;

        

         @ApiModelProperty(value = "内关联表")

         private String innerJoin;

        

         @ApiModelProperty(value = "右关联表")

         private String rightJoin;

        

         @ApiModelProperty(value = "表查询、关联条件")

         private String on;

   

         //获取数据库表信息

         private TableInfoVo tableInfoVo;

        

         public void setTable(String table){

                   if(table != null){

                            String[] tableSplit = table.trim().split("[ ]+");

                            this.tableName = tableSplit[0];

                            if(tableSplit.length > 1){

                                     this.alias = tableSplit[1];

                            }

                   }

         }

        

         public void setLeftJoin(String leftJoin){

                   if(leftJoin != null && this.tableName == null){

                            String[] tableSplit = leftJoin.trim().split("[ ]+");

                            this.joinType = "left join";

                            this.tableName = tableSplit[0];

                            if(tableSplit.length > 1){

                                     this.alias = tableSplit[1];

                            }

                   }

         }

 

         public void setInnerJoin(String innerJoin){

                   if(innerJoin != null && this.tableName == null){

                            String[] tableSplit = innerJoin.trim().split("[ ]+");

                            this.joinType = "inner join";

                            this.tableName = tableSplit[0];

                            if(tableSplit.length > 1){

                                     this.alias = tableSplit[1];

                            }

                   }

         }

        

         public void setRightJoin(String rightJoin){

                   if(rightJoin != null && this.tableName == null){

                            String[] tableSplit = rightJoin.trim().split("[ ]+");

                            this.joinType = "right join";

                            this.tableName = tableSplit[0];

                            if(tableSplit.length > 1){

                                     this.alias = tableSplit[1];

                            }

                   }

         }

}
