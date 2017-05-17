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

import lin.file.service.IFileDirService;

import lin.file.vo.DirMoveVo;

import lin.file.vo.FileDirVo;

import lin.file.vo.FileNodeDetailVo;

 

/**

* @author zwx406636

*/

@Api(value="文件夹相关",produces=MediaType.APPLICATION_JSON_VALUE)

@RestController

@RequestMapping("/api/file/fileDir")

public class FileDirController extends AbstractControllerBase {

        

         private static Logger logger = Logger.getLogger(FileDirController.class);

 

        

        

         @Autowired

         IFileDirService fileDirService;

        

        

        

         @ApiOperation("创建文件夹")

         @PostMapping("/addFileDir")

         @ResponseBody

         public ResponseVo<FileDirVo> addFileDir(

                            @RequestBody FileDirVo fileDirVo){

                  

                   try{

                            String objectId =fileDirVo.getObjectId();

                            String objectType =fileDirVo.getObjectType();

                           

                           

                            if(objectId == null){

                                     return error(7000, "objectId is empty");

                            }

                           

                            if(objectType == null){

                                     return error(7000, "objectType is empty");

                            }

                           

                            Long parentId = fileDirVo.getParentId();

                            if(parentId == 0){

                                     FileDirVo rootFileDirVo = fileDirService.getRootDirByObject(objectType, objectId);

                                     fileDirVo.setParentId(rootFileDirVo.getDirId());

                            }

                           

                            fileDirService.addFileDir(fileDirVo);

                   }catch(Exception e){

                            logger.error(e.getMessage());

                            return error(500,e.getMessage());

                   }

                   return response(fileDirVo);

         }

        

         @ApiOperation("更改文件夹")

         @PostMapping("/updateFileDir")

         @ResponseBody

         public ResponseVo<FileDirVo> updateFileDir(

                            @RequestBody FileDirVo fileDirVo){

                  

                   try{

                            Long dirId = fileDirVo.getDirId();

                            if(dirId == null){

                                     return error(121444, "dirId is empty");

                            }

                           

                           

                            fileDirService.updateFileDir(fileDirVo);

 

                   }catch(Exception e){

                            logger.error(e.getMessage());

                            return error(500,e.getMessage());

                   }

                   return response(fileDirVo);

         }

        

        

         @ApiOperation("获取文件夹信息")

         @GetMapping("/getById")

         @ResponseBody

         public ResponseVo<FileDirVo> getById(@RequestParam("dirId") Long dirId){

                   FileDirVo fileDirVo = null;

                   try{

                            fileDirVo = fileDirService.getById(dirId);

        

                   }catch(Exception e){

                            logger.error(e.getMessage());

                            return error(500,e.getMessage());

                   }

                   return response(fileDirVo);

         }

        

        

        

         @ApiOperation("获取文件夹信息")

         @PostMapping("/getListByParentIdAndObject")

         @ResponseBody

         public ResponseVo<List<FileDirVo>> getListByParentIdAndObject(

                            @RequestBody FileDirVo fileDirVo){

                  

                   List<FileDirVo> fileDirVoList = null;

                   try{

                            Long parentId = fileDirVo.getParentId();

                            if(parentId == null){

                                     return error(70001, "parentId is empty");

                            }

                           

                           

                            String objectId =fileDirVo.getObjectId();

                            String objectType =fileDirVo.getObjectType();

                           

                            if(objectId == null){

                                     return error(70002, "objectId is empty");

                            }

                            if(objectType == null){

                                     return error(70002, "objectType is empty");

                            }

                           

                            if(parentId == 0){

                                     FileDirVo rootFileDirVo = fileDirService.getRootDirByObject(objectType,objectId);

                                     fileDirVo.setParentId(rootFileDirVo.getDirId());

                            }

                           

                           

                            fileDirVoList = fileDirService.getListByParentIdAndObject(fileDirVo);

        

                   }catch(Exception e){

                            logger.error(e.getMessage());

                            return error(500,e.getMessage());

                   }

                   return response(fileDirVoList);

         }

        

        

//      @ApiOperation("删除文件夹包含文件夹里面的文件和子目录")

//      @PostMapping("/delByDirIdAndObject")

//      @ResponseBody

//      public PageResult<FileDir> delByDirIdAndObject(

//                         @RequestBody FileDirVo fileDirVo){

//              

//              

//               Long parentId = fileDirVo.getParentId();

//               if(parentId == null){

//                         return error(70001, "parentId is empty");

//               }

//              

//               String objectId =fileDirVo.getObjectId();

//               String objectType =fileDirVo.getObjectType();

//              

//               if(objectType == null){

//                         return error(70002, "objectType is empty");

//               }

//               if(objectId == null){

//                         return error(70003, "objectId is empty");

//               }

//              

//              

//               fileDirService.delByDirIdAndObject(fileDirVo);

//     

//               return handle(fileDirVo);

//      }

        

         @ApiOperation("跟新文件夹的大小")

         @GetMapping("/uploadFileSizeByObjectAndDirId")

         @ResponseBody

         public ResponseVo<FileDirVo> uploadFileSizeByObjectAndDirId(

                            @RequestParam("objectType") String objectType,

                            @RequestParam(value="objectId") String objectId,

                            @RequestParam(value="dirId") Long dirId){

                   FileDirVo rootDir = null;

                   try{

                            fileDirService.updateFileSizeByObjectAndDirId(objectType, objectId, dirId);

                           

                            rootDir = fileDirService.getRootDirByObject(objectType, objectId);

        

                   }catch(Exception e){

                            logger.error(e.getMessage());

                            return error(500,e.getMessage());

                   }

                   return response(rootDir);

         }

        

        

         @ApiOperation("删除文件夹包含文件夹里面的文件和子目录")

         @GetMapping("/delDirAndFileByObjectAndDirId")

         @ResponseBody

         public ResponseVo<FileNodeDetailVo> delDirAndFileByObjectAndDirId(

                            @RequestParam("objectType") String objectType,

                            @RequestParam(value="objectId") String objectId,

                            @RequestParam(value="dirId") Long dirId){

                   FileNodeDetailVo fileNode = null;

                   try{

                            fileNode = fileDirService.delDirAndFileByObjectAndDirId(objectType, objectId, dirId);

        

                   }catch(Exception e){

                            logger.error(e.getMessage());

                            return error(500,e.getMessage());

                   }

                   return response(fileNode);

         }

        

        

        

         @ApiOperation("文件夹移动")

         @GetMapping("/updateDirMove")

         @ResponseBody

         public ResponseVo<DirMoveVo> updateDirMove(

                            @RequestBody DirMoveVo dirMoveVo){

                  

                   try{

                            String objectId = dirMoveVo.getObjectId();

                            String objectType = dirMoveVo.getObjectType();

                            Long dirId = dirMoveVo.getDirId();

                            Long toDirId = dirMoveVo.getToDirId();

                           

                            if(objectId == null){

                                     return error(7100, "objectId is empty");

                            }

                            if(objectType == null){

                                     return error(7101, "objectType is empty");

                            }

                            if(dirId == null){

                                     return error(7102, "dirId is empty");

                            }

                            if(toDirId == null){

                                     return error(7103, "toDirId is empty");

                            }

                           

                            if(toDirId == 0){

                                     FileDirVo rootDir = fileDirService.getRootDirByObject(objectType, objectId);

                                     toDirId = rootDir.getDirId();

                            }else{

                                    

                                     FileDirVo toDir = fileDirService.getById(toDirId);

                                     if(toDir == null){

                                               return error(7104, "toDirId not support");    

                                     }

                            }

                            FileDirVo fromDir = fileDirService.getById(dirId);

                            if(fromDir == null){

                                     return error(7105, "toDirId is empty");

                            }

                            fromDir.setParentId(toDirId);

                            fileDirService.updateFileDir(fromDir);

                           

                            fileDirService.updateFileSizeByObjectAndDirId(objectType, objectId, 0l);

        

                   }catch(Exception e){

                            logger.error(e.getMessage());

                            return error(500,e.getMessage());

                   }

                   return response(dirMoveVo);

         }

        

        

        

        

        

 

}
