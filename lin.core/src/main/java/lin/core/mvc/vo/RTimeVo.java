package lin.core.mvc.vo;

 

import java.io.Serializable;

import java.util.Date;

 

import io.swagger.annotations.ApiModel;

import lombok.Data;

 

@Data

@ApiModel(value="服务器时间")

public class RTimeVo implements Serializable {/**

         *

          */

         private static final long serialVersionUID = 8631482581088137576L;

 

         private Date curTime;

}
