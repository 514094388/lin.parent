package lin.core.mvc.controller;

 

import java.util.List;

 

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

 

import lin.core.common.AbstractControllerBase;

import lin.core.common.ResponseVo;

import lin.core.mvc.service.IAutoToolsService;

import lin.core.mvc.service.impl.AutoToolsService;

import lin.core.mvc.vo.RTimeVo;

import lin.core.mvc.vo.autotools.QGenerateMVCFromTablesVo;

import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;

import io.swagger.annotations.ApiParam;

 

/**

* 自动化工具模块发布接口类

* @author lWX395320

*

*/

@Api(value="自动化工具模块", produces=MediaType.APPLICATION_JSON_VALUE)

@RestController

@RequestMapping("/api/core/autoTools")

public class AutoToolsController extends AbstractControllerBase {

 

         private static Logger logger = Logger.getLogger(AutoToolsController.class);

        

         @Autowired

         private IAutoToolsService autoToolsService;

        

         @ApiOperation("生成MVC代码")

         @PostMapping("/generateMVCFromTabels")

         public ResponseVo<?> generateMVCFromTabels(

                   @ApiParam(name = "qGenerateMVCFromTablesVoList", value = "生成MVC代码传入参数")

                   @RequestBody List<QGenerateMVCFromTablesVo> qGenerateMVCFromTablesVoList){

 

                   try{

                            for(QGenerateMVCFromTablesVo qGenerateMVCFromTablesVo:qGenerateMVCFromTablesVoList){

                                     autoToolsService.generateMVCFromTabels(qGenerateMVCFromTablesVo);

                            }

                   }catch(Exception e){

                            logger.error(e.getMessage());

                            return error(500,e.getMessage());

                   }

                   return response();

         }

        

 

}
