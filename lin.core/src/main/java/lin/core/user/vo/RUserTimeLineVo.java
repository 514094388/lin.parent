package lin.core.user.vo;

 

import java.io.Serializable;

import java.util.Date;

 

import io.swagger.annotations.ApiModel;

import lombok.Data;

 

/**

* 用户信息bean

*

 * @author lWX395320

*

*/

 

@Data

@ApiModel(value="RUserTimeLineVo")

public class RUserTimeLineVo implements Serializable {

 

         /**

         *

          */

         private static final long serialVersionUID = -5110164342390995814L;

 

         private  Long id;

        

         private  String title;

        

         private  String describe;

        

         private  String detaileContent;

        

         private  Date creationDate;

        

         private  Date lastUpdateDate;

 

}
