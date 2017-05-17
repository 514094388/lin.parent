package lin.core.mvc.controller;

 

import java.util.List;

 

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.bind.annotation.RestController;

 

import lin.core.common.AbstractControllerBase;

import lin.core.common.PageVo;

import lin.core.common.ResponseVo;

import lin.core.mvc.service.IHistoryService;

import lin.core.mvc.vo.QHistoryVo;

import lin.core.mvc.vo.RHistoryVo;

 

import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;

import io.swagger.annotations.ApiParam;

 

/**

* @author zwx406636

*/

@Api(value="日志",produces=MediaType.APPLICATION_JSON_VALUE)

@RestController

@RequestMapping("/api/foundation/history")

public class HistoryController extends AbstractControllerBase {

        

         private static Logger logger = Logger.getLogger(HistoryController.class);

 

         @Autowired

         public IHistoryService historyService;

        

        

         @ApiOperation("getHistoryListByPagination")

         @PostMapping("/getHistoryListByPagination")

         @ResponseBody

         public ResponseVo<PageVo<List<RHistoryVo>>> getHistoryListByPagination(

                            @ApiParam(name = "任务内容", value = "任务内容", required = true)

                            @RequestBody

                            QHistoryVo  qHistoryVo){

                   PageVo<List<RHistoryVo>> rHistoryVoListPage = null;

                   try{

                            rHistoryVoListPage = historyService.getHistoryListByPagination(qHistoryVo);

        

                   }catch(Exception e){

                            e.getStackTrace();

                            return error(500,e.getMessage());

                   }

                   return response(rHistoryVoListPage);

         }

        

        

 

}
