package lin.file.controller;

 

import java.io.IOException;

import java.util.Map;

 

import javax.servlet.ServletOutputStream;

import javax.servlet.http.HttpServletResponse;

 

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

 

import lin.core.common.AbstractControllerBase;

import lin.core.common.ResponseVo;

 

import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;

import io.swagger.annotations.ApiParam;

import lin.file.service.IWopiService;

 

@Api(value="WOPI相关服务",produces=MediaType.APPLICATION_JSON_VALUE)

@RestController

@RequestMapping("/api/file/wopi")

public class FileWopiController extends AbstractControllerBase  {

         private static Logger logger = Logger.getLogger(FileWopiController.class);

        

         @Autowired

         private IWopiService wopiService;

        

         @ApiOperation("获取文件相关信息")

         @GetMapping("/files/{fileId}")

         public void checkFileInfo(HttpServletResponse response,

                            @ApiParam(value = "fileId")

                            @PathVariable Long fileId){

                   ServletOutputStream out = null;

                   try{

                            out = response.getOutputStream();

                            logger.info("get file info...");

                            wopiService.checkFileInfo(response, fileId);

                           

                   }catch(Exception e){

                            logger.error(e.getMessage());

                            try {

                                     out.print(errorToJson(500,e.getMessage()));

                            } catch (IOException e1) {

                                     // TODO Auto-generated catch block

                                     logger.error(e1.getMessage());

                            }

                   }

         }

        

         @ApiOperation("读取文件")

         @GetMapping("/files/{fileId}/contents")

         public void getFile(HttpServletResponse response,

                            @ApiParam(value = "fileId")

                            @PathVariable Long fileId){

                   ServletOutputStream out = null;

                   try{

                            out = response.getOutputStream();

                            logger.info("get file...");

                            wopiService.getFile(response, fileId);

                   }catch(Exception e){

                            logger.error(e.getMessage());

                            try {

                                     out.print(errorToJson(500,e.getMessage()));

                            } catch (IOException e1) {

                                     // TODO Auto-generated catch block

                                      logger.error(e1.getMessage());

                            }

                   }

         }

        

         @ApiOperation("预览文件")

         @GetMapping("/files/viewFile/{fileId}")

         public ResponseVo<Map<String, String>> viewFile(

                            @ApiParam(value = "fileId")

                            @PathVariable Long fileId){

                   Map<String, String> resultMap = null;

                   try{

                            logger.info("view file...");

                            resultMap = wopiService.viewFile(fileId);

                   }catch(Exception e){

                            logger.error(e.getMessage());

                            return error(500,e.getMessage());

                   }

                   return response(resultMap);

         }

}
