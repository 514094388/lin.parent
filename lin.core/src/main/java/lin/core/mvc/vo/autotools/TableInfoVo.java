package lin.core.mvc.vo.autotools;

 

import java.io.ByteArrayInputStream;

import java.io.ByteArrayOutputStream;

import java.io.IOException;

import java.io.ObjectInputStream;

import java.io.ObjectOutputStream;

import java.io.Serializable;

import java.util.Date;

import java.util.List;

 

import io.swagger.annotations.ApiModel;

import io.swagger.annotations.ApiModelProperty;

import lin.core.util.Names;

import lombok.Data;

 

 

@Data

@ApiModel(value="TableInfoVo",description="表结构信息")

public class TableInfoVo implements Serializable  {

        

         /**

         *

          */

         private static final long serialVersionUID = 4175950187080931327L;

 

         @ApiModelProperty("表名称")

         private String tableName;

        

         @ApiModelProperty("表下标，用于生成表别名和区分开表中的同名列用")

         private int tableIndex;

        

         private Names names;

        

         @ApiModelProperty("表注释")

         private String comments;

        

         @ApiModelProperty("表字段信息")

         private List<TableColumnsInfoVo> tableColumnsInfoVoList;

        

         public TableInfoVo copy() throws CloneNotSupportedException, IOException, ClassNotFoundException {

                   // TODO Auto-generated method stub

                   ByteArrayOutputStream bos = new ByteArrayOutputStream();

                   ObjectOutputStream oos = new ObjectOutputStream(bos);

                   oos.writeObject(this);

 

                   ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));

                   return (TableInfoVo)ois.readObject();

         }

 

}
