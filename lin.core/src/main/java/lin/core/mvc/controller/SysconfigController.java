package lin.core.mvc.controller;

 

import java.util.List;

 

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

 

import lin.core.common.AbstractControllerBase;

import lin.core.common.ResponseVo;

import lin.core.mvc.service.ISysconfigService;

import lin.core.mvc.vo.RTaskCategoryDefaultVo;

 

import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;

/**

* 系统配置信息操作发布接口类

* @author lWX395320

*

*/

@Api(value="系统配置信息模块",produces=MediaType.APPLICATION_JSON_VALUE)

@RestController

@RequestMapping("/api/core/sysconfig")

public class SysconfigController extends AbstractControllerBase {

        

         @Autowired

         ISysconfigService sysconfigService;

        

         @ApiOperation("请求默认任务类别")

         @GetMapping("/getTaskCategoryDefault")

         public ResponseVo<List<RTaskCategoryDefaultVo>> getTaskCategoryDefault(){

                   List<RTaskCategoryDefaultVo> rTaskCategoryDefaultList = null;

                   try{

                            rTaskCategoryDefaultList = sysconfigService.getTaskCategoryDefault();

                   }catch(Exception e){

                            e.getStackTrace();

                            return error(500,e.getMessage());

                   }

                   return response(rTaskCategoryDefaultList);

         }

}
