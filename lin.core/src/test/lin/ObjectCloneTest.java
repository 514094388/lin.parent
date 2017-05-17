package lin.core;

 

import java.util.HashMap;

import java.util.Map;

 

import io.swagger.annotations.ApiModelProperty;

 

public class ObjectCloneTest {

 

         public static void main(String[] args) {

                   // TODO Auto-generated method stub

                   TableColumnsInfoVo tableColumnsInfoVo1 = new TableColumnsInfoVo();

                   tableColumnsInfoVo1.alias = "tt1";

                   Map<String ,TableColumnsInfoVo> tableColumnsInfoVoMap1 = new HashMap<String,TableColumnsInfoVo>();

                   tableColumnsInfoVoMap1.put("1", tableColumnsInfoVo1);

                   Map<String ,TableColumnsInfoVo> tableColumnsInfoVoMap2 = tableColumnsInfoVoMap1;

         }

 

}

 

 

class TableColumnsInfoVo{

         public String columnName;

        

         @ApiModelProperty("表字段java名称")

         public String columnJavaName;

        

         @ApiModelProperty("表别名")

         public String alias;

        

         @ApiModelProperty("是否主键")

         public boolean isPrimaryKey;

        

         @ApiModelProperty("表字段数据类型java.sql.Types里面的jdbcType类型名称 ")

         public String typeName;

        

         @ApiModelProperty("表字段数据类型java.sql.Types里面的jdbcType类型编号 ")

         public int dataType;

 

         @ApiModelProperty("根据dataType获取对应的JDBCType ")

         private String jdbcType;

        

         @ApiModelProperty("根据dataType获取对应的java class")

         public Class javaClass;

        

         @ApiModelProperty("废弃,表字段数据类型长度")

         public Long dataLength;

        

         @ApiModelProperty("表字段注释")

         public String comments;

}
