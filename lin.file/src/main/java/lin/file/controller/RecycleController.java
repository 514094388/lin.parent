package lin.file.controller;

 

import java.util.List;

 

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.bind.annotation.RestController;

 

import lin.core.common.AbstractControllerBase;

import lin.core.common.ResponseVo;

 

import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;

import lin.file.service.IFileDirLinkService;

import lin.file.service.IFileDirService;

import lin.file.service.IFileService;

import lin.file.service.IRecycleService;

import lin.file.vo.RecycleVo;

 

/**

* @author zwx406636

*/

@Api(value="回收站相关",produces=MediaType.APPLICATION_JSON_VALUE)

@RestController

@RequestMapping("/api/file/recycle")

public class RecycleController extends AbstractControllerBase {

        

         private static Logger logger = Logger.getLogger(RecycleController.class);

 

        

         @Autowired

         IRecycleService recycleService;

        

         @Autowired

         IFileDirService fileDirService;

        

         @Autowired

         IFileDirLinkService fileDirLinkService;

        

         @Autowired

         private IFileService fileService;

        

        

         @ApiOperation("创建一条回收站信息 前台应该不会调用")

         @PostMapping("/createRecycle")

         @ResponseBody

         public ResponseVo<RecycleVo> createRecycle(

                            @RequestBody RecycleVo recycleVo){

                   try{

                            logger.info(recycleVo);

                           

                           

                            recycleService.createRecycle(recycleVo);

                  

                           

                   }catch(Exception e){

                            logger.error(e.getMessage());

                            return error(500,e.getMessage());

                   }

                   return response(recycleVo);

                  

         }

        

         @ApiOperation("根据 objectType objectId 来查询 List 记录")

         @GetMapping("/getFileListByObject")

         @ResponseBody

         public ResponseVo<List<RecycleVo>> getFileListByObject(

                            @RequestParam("objectType") String objectType,

                            @RequestParam(value="objectId") String objectId

                            ){

                   List<RecycleVo> recycleVoList = null;

                   try{

                            RecycleVo recycleVo = new RecycleVo();

                            recycleVo.setObjectId(Long.valueOf(objectId));

                            recycleVo.setObjectType(Long.valueOf(objectType));

                            recycleVoList = recycleService.getFileListByObject(recycleVo);

        

                   }catch(Exception e){

                            logger.error(e.getMessage());

                            return error(500,e.getMessage());

                   }

                   return response(recycleVoList);

         }

        

        

         @ApiOperation("根据 RecycleId 来查询 记录")

         @GetMapping("/getRecycleByRecycleId")

         @ResponseBody

         public ResponseVo<RecycleVo> getRecycleByRecycleId(

                            @RequestParam("recycleId") Long recycleId){

                   RecycleVo recycleVo = null;

                   try{

                            recycleVo = recycleService.getRecycleByRecycleId(recycleId);

        

                   }catch(Exception e){

                            logger.error(e.getMessage());

                            return error(500,e.getMessage());

                   }

                   return response(recycleVo);

         }

        

         @ApiOperation("根据 RecycleId删除记录")

         @PostMapping("/delByRecycleId")

         @ResponseBody

         public ResponseVo<RecycleVo> delByRecycleId(

                            @RequestBody RecycleVo recycleVo){

                   try{

                            if(recycleVo!=null){

                                     RecycleVo recycleVoReal = recycleService.getRecycleByRecycleId(recycleVo.getRecycleId());

                                     if(recycleVoReal!=null){

                                               recycleVoReal.setIsDelete(1L);

                                               recycleService.updateRecycle(recycleVoReal);

                                     }else{

                                               return error(600,"找不到记录");

                                     }

                            }else{

                                     return error(600,"传过来的对象为空");

                            }

                   }catch(Exception e){

                            logger.error(e.getMessage());

                            return error(500,e.getMessage());

                   }

                   return response(recycleVo);

         }

        

         @ApiOperation("根据 RecycleId 还原  前台应该不会调用")

         @PostMapping("/updateRecoverByRecycleId")

         @ResponseBody

         public ResponseVo<RecycleVo> updateRecoverByRecycleId(

                            @RequestBody RecycleVo recycleVo){

                   try{

                            recycleService.updateRecoverByRecycleId(recycleVo);

 

                   }catch(Exception e){

                            logger.error(e.getMessage());

                            return error(500,e.getMessage());

                   }

                   return response(recycleVo);

         }

        

         @ApiOperation("根据 RecycleId 还原")

         @PostMapping("/reCoverRecoverByRecycleId")

         @ResponseBody

         public ResponseVo<RecycleVo> reCoverRecoverByRecycleId(

                            @RequestBody RecycleVo recycleVo){

                   try{

                            if(recycleVo!=null){

                                     if(recycleVo.getRecycleId()!=null){

                                               RecycleVo recycleReal = recycleService.getRecycleByRecycleId(recycleVo.getRecycleId());

                                               if(recycleReal!=null){

                                                        if(recycleReal.getObjectType() == 0L){//文件还原

                                                                 fileDirLinkService.recycleFileDirLink(recycleReal);

                                                        }else if(recycleReal.getObjectType() == 1L){//文件夹还原

                                                                 fileDirService.recycleFileDir(recycleReal);

                                                        }else if(recycleReal.getObjectType() == 2L){//版本还原

                                                                 fileService.recycleFile(recycleReal);

                                                        }

                                               }else{

                                                        return error(600,"不存在的记录");

                                               }

                                     }

                            }else{

                                     return error(600,"传过来的对象为空");

                            }

                   }catch(Exception e){

                            logger.error(e.getMessage());

                            return error(500,e.getMessage());

                   }

                   return response(recycleVo);

         }

        

}
