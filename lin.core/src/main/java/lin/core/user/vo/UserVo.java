package lin.core.user.vo;

 

import java.sql.Timestamp;

 

import io.swagger.annotations.ApiModel;

import lombok.Data;

 

 

@Data

@ApiModel(value="UserVo",description="UserVo")

public class UserVo {

        

         private Long id;

        

         private String account;

        

         private String password;

        

         private String nameCn;

        

         private String email;

        

         private Timestamp createdate;

        

         private Integer isLockedOut;

        

         private Integer isHintEnabled;

        

         private Timestamp lastLoginDate;

        

         private Long incorrectLoginAttempt;

        

         private String empNo;

        

         private String nameEn;

        

         private String photoUrl;

 

}
