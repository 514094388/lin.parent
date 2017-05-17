package lin.file.controller;

 

import java.io.File;

import java.io.FileInputStream;

import java.io.FileNotFoundException;

import java.io.IOException;

 

import javax.servlet.ServletOutputStream;

import javax.servlet.http.HttpServletResponse;

 

import org.apache.commons.io.IOUtils;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;

 

import com.alibaba.fastjson.JSON;

import lin.core.common.AbstractControllerBase;

 

import io.swagger.annotations.Api;

import lin.file.service.IFileService;

import lin.file.vo.FileVo;

 

/**

* @author zwx406636

*/

@Controller

@Api(value="资源获取")

@RequestMapping("api/file/resource")

public class ResourceController extends AbstractControllerBase {

        

         private static Logger logger = Logger.getLogger(ResourceController.class);

 

        

         @Value("${uploadPath}")

         private String uploadPath;

        

         @Autowired

         private IFileService fileService;

        

        

         @GetMapping("/fileId/{fileId}")

         public void getFileByFileId(@PathVariable("fileId") Long fileId,HttpServletResponse response){

                   getFileId(fileId, response);

         }

         @GetMapping("test")

         public void test(HttpServletResponse response){

                   try {

                            ServletOutputStream out= response.getOutputStream();

                            out.print(JSON.toJSONString(response()));

                   } catch (IOException e) {

                            // TODO Auto-generated catch block

                            logger.error(e.getMessage());

                   }

                  

         }

        

         @GetMapping("/fId/{fId}")

         public void getFileByFId(@PathVariable("fId") Long fId,HttpServletResponse response){

                   getFileId(fId, response);

         }

        

         private void getFileId(Long fId,HttpServletResponse response){

                   ServletOutputStream out = null;

                   try {

                           

                            out = response.getOutputStream();

                                    

                            FileVo fileVo = fileService.getFileByFid(fId);

                           

                            if(fileVo == null){

                                     out.print(errorToJson(600,"fileId empty"));

                            }

                            String filePath = fileVo.getFilePath();

                            File file = new File(uploadPath+filePath);

                            FileInputStream fileIn = new FileInputStream(file);

                            IOUtils.copy(fileIn, out);

                            fileIn.close();

                            out.close();

                   }catch(Exception e){

                            logger.error(e.getMessage());

                            try {

                                     out.print(errorToJson(500,e.getMessage()));

                            } catch (IOException e1) {

                                     logger.error(e1.getMessage());

                            }       

                   }

         }

}
