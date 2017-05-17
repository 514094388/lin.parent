package lin.core.mvc.vo.autotools;

 

import java.io.Serializable;

import java.util.List;

 

import io.swagger.annotations.ApiModel;

import io.swagger.annotations.ApiModelProperty;

import lombok.Data;

 

@Data

@ApiModel(value="qTableLinkListVo",

    description="指定多表关联表，"

            + "table属性指定的表为主表，只能有一个，如果指定了多个，则取第一个为主表，其他的视为内关联表。"

            + "示例：[{\"table\":\"EUI_TASK t\",\"on\":\"t.is_delete = 0\"},"

            + "{\"leftJoin\":\"EUI_TASK_TAG_LINK t1\",\"on\":\"t.id = t1.task_id\"},"

            + "{\"leftJoin\":\"EUI_TAG t2\",\"on\":\"t1.tag_id = t2.id\"}]")

public class QTableLinkListVo implements Serializable  {

   

 

    /**

    *

     */

    private static final long serialVersionUID = -431755370743426357L;

   

    private List<QTableLinkVo> qTableLinkVoList;

}

 
