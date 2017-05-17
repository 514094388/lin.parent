package lin.core.mvc.vo;

 

import java.io.Serializable;

 

import io.swagger.annotations.ApiModel;

import lombok.Data;

 

@Data

@ApiModel(value="返回默认任务类别")

public class RTaskCategoryDefaultVo implements Serializable {/**

         *

          */

         private static final long serialVersionUID = 8631482581088137576L;

 

         private String id;

        

         private String categoryName ;

        

         private String sort;

}
