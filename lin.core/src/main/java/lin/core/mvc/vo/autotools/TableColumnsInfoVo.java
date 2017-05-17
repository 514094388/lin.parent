package lin.core.mvc.vo.autotools;

 

import java.io.Serializable;

import java.sql.JDBCType;

import java.util.Date;

 

import io.swagger.annotations.ApiModel;

import io.swagger.annotations.ApiModelProperty;

import lin.core.util.JdbcTypes;

import lin.core.util.Names;

import lombok.Data;

 

 

@Data

@ApiModel(value="TableInfoVo",description="表结构信息")

public class TableColumnsInfoVo implements Serializable {

        

         /**

         *

          */

         private static final long serialVersionUID = -6194173135178181525L;

 

         @ApiModelProperty("表字段名称")

         private String columnName;

        

         @ApiModelProperty("表字段java名称")

         private String columnJavaName;

        

         @ApiModelProperty("表别名")

         private String alias;

        

         @ApiModelProperty("是否主键")

         private boolean isPrimaryKey;

        

         @ApiModelProperty("表字段数据类型java.sql.Types里面的jdbcType类型名称 ")

         private String typeName;

        

         @ApiModelProperty("表字段数据类型java.sql.Types里面的jdbcType类型编号 ")

         private int dataType;

 

         @ApiModelProperty("根据dataType获取对应的JDBCType ")

         private String jdbcType;

        

         @ApiModelProperty("根据dataType获取对应的java class")

         private Class javaClass;

        

         @ApiModelProperty("废弃,表字段数据类型长度")

         private Long dataLength;

        

         @ApiModelProperty("表字段注释")

         private String comments;

        

         public void setColumnName(String columnName){

                   this.columnName = columnName;

                   this.columnJavaName = Names.transformDBNameToJavaName(1, columnName);

         }

        

         public void setAlias(String alias){

                   this.alias = alias;

                   if(this.columnJavaName != null){

                            this.columnJavaName = this.columnJavaName + Names.firstCharToUpOrToLow(0, alias);

                   }

         }

 

         public void setDataType(int dataType){

                   this.dataType = dataType;

                   this.jdbcType = JDBCType.valueOf(dataType).getName();

                   this.javaClass = JdbcTypes.valueOf(this.jdbcType).getJavaClass();

         }

        

}
