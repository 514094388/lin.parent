package lin.core.mvc.controller;

 

import java.util.Date;

 

import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

 

import lin.core.common.AbstractControllerBase;

import lin.core.common.ResponseVo;

import lin.core.mvc.vo.RTimeVo;

 

import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;

 

/**

* 用户中心操作发布接口类

* @author lWX395320

*

*/

@Api(value="服务器时间",produces=MediaType.APPLICATION_JSON_VALUE)

@RestController

@RequestMapping("/api/foundation/time")

public class TimeController extends AbstractControllerBase {

        

 

        

         @ApiOperation("请求服务器当前时间")

         @GetMapping("/getCurTime")

         public ResponseVo<RTimeVo> getCurTime(){

                   RTimeVo rTimeVo = null;

                   try{

                            rTimeVo = new RTimeVo();

                            rTimeVo.setCurTime(new Date());

        

                   }catch(Exception e){

                            e.getStackTrace();

                            return error(500,e.getMessage());

                   }

                   return response(rTimeVo);

         }

        

 

}
