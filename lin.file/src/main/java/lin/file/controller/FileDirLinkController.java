package lin.file.controller;

 

import java.util.ArrayList;

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

import io.swagger.annotations.ApiParam;

import lin.file.service.IFileDirLinkService;

import lin.file.service.IFileDirService;

import lin.file.service.IFileService;

import lin.file.vo.FileDeleteVo;

import lin.file.vo.FileDirLinkVo;

import lin.file.vo.FileDirVo;

import lin.file.vo.FileNodeDetailVo;

import lin.file.vo.FileNodeVo;

import lin.file.vo.FileVo;

 

/**

* @author zwx406636

*/

@Api(value="文件夹与文件相关",produces=MediaType.APPLICATION_JSON_VALUE)

@RestController

@RequestMapping("/api/file/fileDirLink")

public class FileDirLinkController extends AbstractControllerBase {

        

         private static Logger logger = Logger.getLogger(FileDirLinkController.class);

 

        

         @Autowired

         IFileDirLinkService fileDirLinkService;

        

        

         @Autowired

         IFileDirService fileDirService;

        

         @Autowired

         private IFileService fileService;

        

        

//      @Autowired

//      private IUserService userService;

//     

//      @Autowired

//      private IRecycleService recycleService;

        

         private ResponseVo<FileDirLinkVo> checkFileDirLink(FileDirLinkVo fileDirLinkVo) {

                   Long dirId = fileDirLinkVo.getDirId();

                   if(dirId == null || dirId == 0){

                            return error(80013, "dirId is empty");

                   }

                   FileDirVo fileDirVo = fileDirService.getById(dirId);

                   if(fileDirVo == null || fileDirVo.getDirId() == null){

                            return error(80014, "fileDirVo is empty");

                   }

                  

                   Long fId = fileDirLinkVo.getFId();

                   if(fId == null || fId == 0){

                            return error(80016, "fId is empty");

                   }

                   FileVo fileVo = fileService.getFileByFid(fId);

                   if(fileVo == null || fileVo.getFileId() == null){

                            return error(80015, "euiFile is empty");

                   }

                   return null;

         }

        

        

        

        

         @ApiOperation("创建文件和文件夹的关系文件夹")

         @PostMapping("/addFileDirLink")

         @ResponseBody

         public ResponseVo<FileDirLinkVo> addFileDirLink(

                            @RequestBody FileDirLinkVo fileDirLinkVo){

                   try{

                            ResponseVo<FileDirLinkVo> pageR = checkFileDirLink(fileDirLinkVo);

                            if(pageR != null){

                                     return pageR;

                            }

                           

                            fileDirLinkService.addFileDirLink(fileDirLinkVo);

        

                   }catch(Exception e){

                            logger.error(e.getMessage());

                            return error(500,e.getMessage());

                   }

                   return response(fileDirLinkVo);

         }

        

         @ApiOperation("更改文件与文件夹的关系")

         @PostMapping("/updateEUIFileDirLink")

         @ResponseBody

         public ResponseVo<FileDirLinkVo> updateFileDirLink(

                            @RequestBody FileDirLinkVo fileDirLinkVo){

                   try{

                            ResponseVo<FileDirLinkVo> pageR = checkFileDirLink(fileDirLinkVo);

                            if(pageR != null){

                                     return pageR;

                            }

                           

                           

                            fileDirLinkService.updateFileDirLink(fileDirLinkVo);

        

                   }catch(Exception e){

                            logger.error(e.getMessage());

                            return error(500,e.getMessage());

                   }

                   return response(fileDirLinkVo);

         }

        

        

         @ApiOperation("获取文件与文件夹的关系")

         @GetMapping("/getById")

         @ResponseBody

         public ResponseVo<FileDirLinkVo> getById(@RequestParam("fDirId") Long fDirId){

                   FileDirLinkVo fileDirLinkVo = null;

                   try{

                            fileDirLinkVo = fileDirLinkService.getById(fDirId);

        

                   }catch(Exception e){

                            logger.error(e.getMessage());

                            return error(500,e.getMessage());

                   }

                   return response(fileDirLinkVo);

         }

        

        

         @ApiOperation("获取整个文件系统")

         @GetMapping("/getFileSystemNode")

         @ResponseBody

         public ResponseVo<FileNodeVo> getFileSystemNode(

                            @RequestParam("objectType") String objectType,

                            @RequestParam(value="objectId") String objectId,

                            @RequestParam(value="dirId") Long dirId,

                            @RequestParam(value="srchText",required=false)@ApiParam(name = "srchText", value = "文件名模糊查找", required = false) String srchText){

                   FileNodeVo rFileNodeVo  = null;

                   try{

                            //将大写全部转换成小写，不区分大小写查询

                            srchText = srchText.toLowerCase();

                            FileNodeDetailVo fileNode = fileDirLinkService.getFileSystemNodeByDirId(objectType, objectId, dirId,srchText, null);

                           

                            rFileNodeVo  = new FileNodeVo();

                            changeToRFileNodeVo(fileNode, rFileNodeVo);

        

                   }catch(Exception e){

                            logger.error(e.getMessage());

                            return error(500,e.getMessage());

                   }

                   return response(rFileNodeVo);

         }

        

         /*

         * 前台强烈要求这样的返回结构。

         * 这个本来我不想这样做的 

          * 他为了自己好理解 强烈要求这样的结构

         * 出错了单独调试

         *

         */

         private void changeToRFileNodeVo(FileNodeDetailVo dirNode,FileNodeVo rFileNodeVo) {

                  

                   rFileNodeVo.setFileId(dirNode.getDirId());

                   rFileNodeVo.setContentType(dirNode.getContentType());

                   rFileNodeVo.setFileName(dirNode.getDirName());

                   rFileNodeVo.setFileSize(dirNode.getDirSize());

                   rFileNodeVo.setParentId(dirNode.getParentId());

                   rFileNodeVo.setCreationDate(dirNode.getCreationDate());

                   rFileNodeVo.setCreatedBy(dirNode.getCreatedBy());

                   rFileNodeVo.setCreatorName(dirNode.getCreatorName());

                   rFileNodeVo.setLastUpdateDate(dirNode.getLastUpdateDate());

                   rFileNodeVo.setModifiorName(dirNode.getModifiorName());

                   rFileNodeVo.setLastUpdatedBy(dirNode.getLastUpdatedBy());

                   List<FileNodeDetailVo> childNodeList = dirNode.getChildNode();

                  

                   if(childNodeList == null){

                            return;

                   }

                  

                   List<FileNodeVo> rFileNodeVoList =  new ArrayList<FileNodeVo>();

                  

                   for (FileNodeDetailVo fileNode : childNodeList) {

                            String contentType = fileNode.getContentType();

                            FileNodeVo rFileNodeVo1 = new FileNodeVo();

                           

                            rFileNodeVo1.setContentType(fileNode.getContentType());

                           

                            if(contentType == null || contentType.equals("")){

                                     changeToRFileNodeVo(fileNode,rFileNodeVo1);

                            }else{

                                     rFileNodeVo1.setFileId(fileNode.getFileId());

                                     rFileNodeVo1.setFileName(fileNode.getFileRealName());

                                     rFileNodeVo1.setFileSize(fileNode.getFileSize());

                                     rFileNodeVo1.setFId(fileNode.getFId());

                                     rFileNodeVo1.setParentId(fileNode.getDirId());

                                     rFileNodeVo1.setCreationDate(fileNode.getCreationDate());

                                     rFileNodeVo1.setCreatedBy(fileNode.getCreatedBy());

                                     rFileNodeVo1.setCreatorName(fileNode.getCreatorName());

                                     rFileNodeVo1.setLastUpdateDate(fileNode.getLastUpdateDate());

                                     rFileNodeVo1.setModifiorName(fileNode.getModifiorName());

                                     rFileNodeVo1.setLastUpdatedBy(fileNode.getLastUpdatedBy());

                            }

                            rFileNodeVoList.add(rFileNodeVo1);

                   }

 

                   rFileNodeVo.setChildNode(rFileNodeVoList);

 

         }

        

        

        

        

         @ApiOperation("根据RFileNodeVo来删除文件或者文件夹")

         @PostMapping("/delByRFileNodeVo")

         @ResponseBody

         public ResponseVo<List<FileNodeVo>> delByRFileNodeVo(

                                               @RequestBody List<FileNodeVo> rFileNodeVoList){

                  

                   try{

                            for (FileNodeVo rFileNodeVo : rFileNodeVoList) {

                                     Long fileId = rFileNodeVo.getFileId();

                                     if(fileId == null){

                                               return error(9002, "fileId is empty");

                                     }

                            }

                           

                            for (FileNodeVo rFileNodeVo : rFileNodeVoList) {

                                     String contentType = rFileNodeVo.getContentType();

                                     if(contentType == null || contentType.equals("")){

        

                                               Long fileId = rFileNodeVo.getFileId();

                                               FileDirVo fileDirVo = fileDirService.getById(fileId);

                                               if(fileDirVo == null){

                                                        return error(9004, "dirId not support");

                                               }

                                               fileDirService.updateDirAndSaveDirInfo(fileDirVo,rFileNodeVo.getLastUpdateDate());

                                     }else{

                                               fileDirLinkService.updateFileDirLinkVoAndSaveRecycle(rFileNodeVo);

                                               /*

                                               * 这个地方没有加哪个插入回收站的代码 我没有想好

                                               */

                                     }

                            }

        

                   }catch(Exception e){

                            logger.error(e.getMessage());

                            return error(500,e.getMessage());

                   }

                   return response(rFileNodeVoList);

         }

        

         @ApiOperation("根据版本ID删除版本")

         @PostMapping("/deleteVersion")

         @ResponseBody

         public ResponseVo<FileDeleteVo> deleteVersion(@RequestBody List<String> fileDeleteList){

                   FileDeleteVo fileDeleteVo = new FileDeleteVo();

                   List<String> deleteFileId = new ArrayList<String>();

                   try{

                            deleteFileId = fileService.updateFileAndSaveRecycleByFileId(fileDeleteList);

                            fileDeleteVo.setIsDelete(true);

                            fileDeleteVo.setDeleteFiles(deleteFileId);

                            fileDeleteVo.setWithoutDeleteFiles(new ArrayList<String>());

                   }catch(Exception e){

                            fileDeleteVo.setIsDelete(false);

                            fileDeleteVo.setDeleteFiles(deleteFileId);

                            fileDeleteVo.setWithoutDeleteFiles(getHaveNotDeleteFiles(fileDeleteList,deleteFileId));

                            logger.error(e.getMessage());

                   }

                   return response(fileDeleteVo);

         }

        

        

        

         @ApiOperation("移动文件夹或者文件到新的文件夹中间")

         @PostMapping("/moveFileToDirId")

         @ResponseBody

         public ResponseVo<List<FileNodeVo>> moveFileToDirId(

                            @RequestParam(value="toDirId") Long toDirId,

                            @RequestBody List<FileNodeVo> rFileNodeVoList){

                   try{

                            if(toDirId == null){

                                     return error(9001, "toDirId is empty");

                            }

                           

                            if(toDirId == 0){

                                     return error(9012, "toDirId not support");

                            }

                           

                            FileDirVo fileDirVo = fileDirService.getById(toDirId);

                            if(fileDirVo == null){

                                     return error(9002, "toDirId not support");

                            }

                           

                            for (FileNodeVo rFileNodeVo : rFileNodeVoList) {

                                     Long fileId = rFileNodeVo.getFileId();

                                     if(fileId == null){

                                               return error(9002, "fileId is empty");

                                     }

                            }

                           

                            for (FileNodeVo rFileNodeVo : rFileNodeVoList) {

                                     String contentType = rFileNodeVo.getContentType();

                                     if(contentType != null && !contentType.isEmpty()){

                                               Long fId = rFileNodeVo.getFId();

                                               FileDirLinkVo fileDirLinkVo =fileDirLinkService.getByfId(fId);

                                               if(fileDirLinkVo == null){

                                                        return error(9005, "fid not support");

                                               }

                                               fileDirLinkVo.setDirId(toDirId);

                                               fileDirLinkService.updateFileDirLink(fileDirLinkVo);

                                     }else{                                             

                                               Long dirId = rFileNodeVo.getFileId();

                                               fileDirVo = fileDirService.getById(dirId);

                                               if(fileDirVo == null){

                                                        return error(9004, "dirId not support");

                                               }

                                               fileDirVo.setParentId(toDirId);

                                               fileDirService.updateFileDir(fileDirVo);

                                     }

                            }

        

                   }catch(Exception e){

                            logger.error(e.getMessage());

                            return error(500,e.getMessage());

                   }

                   return response(rFileNodeVoList);

         }

        

        

         @ApiOperation("复制文件夹或者文件到新的文件夹中间")

         @PostMapping("/copyFileToDirId")

         @ResponseBody

         public ResponseVo<List<FileNodeVo>> copyFileToDirId(

                            @RequestParam(value="toDirId") Long toDirId,

                            @RequestBody List<FileNodeVo> rFileNodeVoList){

                   try{

                            if(toDirId == null){

                                     return error(9001, "toDirId is empty");

                            }

                           

                           

                            if(toDirId == 0){

                                     return error(9012, "toDirId not support");

                            }

                           

                           

                            FileDirVo fileDirVo = fileDirService.getById(toDirId);

                            if(fileDirVo == null){

                                     return error(9002, "fileDirVo not support");

                            }

                           

                            for (FileNodeVo rFileNodeVo : rFileNodeVoList) {

                                     Long fileId = rFileNodeVo.getFileId();

                                     if(fileId == null){

                                               return error(9002, "fileId is empty");

                                     }

                            }

                           

                            for (FileNodeVo rFileNodeVo : rFileNodeVoList) {

                                     String contentType = rFileNodeVo.getContentType();

                                     if(contentType == null || contentType.equals("")){

                                               Long dirId = rFileNodeVo.getFileId();

                                               fileDirVo = fileDirService.getById(dirId);

                                               if(fileDirVo == null){

                                                        return error(9004, "dirId not support");

                                               }

                                               fileDirVo.setParentId(toDirId);

                                               fileDirService.copyFileDir(fileDirVo,toDirId);

                                     }else{

                                               Long fId = rFileNodeVo.getFId();

                                               FileDirLinkVo fileDirLinkVo =fileDirLinkService.getByfId(fId);

                                               if(fileDirLinkVo == null){

                                                        return error(9005, "fid not support");

                                               }

                                               fId = fileService.copyFile(fId);

                                               fileDirLinkVo.setFId(fId);

                                               fileDirLinkVo.setDirId(toDirId);

                                               fileDirLinkService.addFileDirLink(fileDirLinkVo);

                                     }

                            }

        

                   }catch(Exception e){

                            logger.error(e.getMessage());

                            return error(500,e.getMessage());

                   }

                   return response(rFileNodeVoList);

         }

        

         @ApiOperation("根据 RFileNodeVo来更改文件名 ")

         @PostMapping("/changeFileName")

         @ResponseBody

         public ResponseVo<FileNodeVo> changeFileName(

                            @RequestBody FileNodeVo rFileNodeVo){

                   try{

                            Long id = rFileNodeVo.getFileId();

                            if(id == null){

                                     return error(9002, "fileId is empty");

                            }

                  

                            String contentType = rFileNodeVo.getContentType();

                            if(contentType == null || contentType.equals("")){

                                    

                                     FileDirVo fileDirVo = fileDirService.getById(id);

                                     fileDirVo.setDirName(rFileNodeVo.getFileName());

                                     fileDirService.updateFileDir(fileDirVo);

                                    

                            }else{

                                     FileVo fileVo = fileService.getFileByFileId(id);

                                     fileVo.setFileRealName(rFileNodeVo.getFileName());

                                     fileService.updateFileByFileId(fileVo);

                            }

        

                   }catch(Exception e){

                            logger.error(e.getMessage());

                            return error(500,e.getMessage());

                   }

                   return response(rFileNodeVo);

         }

        

        

         @ApiOperation("查询文件夹结构")

         @GetMapping("/getFileNodeById")

         @ResponseBody

         public ResponseVo<List<FileNodeVo>> getFileNodeById(

                            @ApiParam(value = "文件id，有多个值中间用逗号分隔", required = false)

                            @RequestParam("fileId") String fileId,

                            @ApiParam(value = "文件夹id，有多个值中间用逗号分隔", required = false)

                            @RequestParam("dirId") String dirId){

                   List<FileNodeVo> rFileNodeVoList = null;

                   try{

                            rFileNodeVoList = new ArrayList<FileNodeVo>();

                            if(dirId!=null&&dirId.length()>0)

                            {

                                     String[] dirids=dirId.split(",");

                                     for(String strid:dirids)

                                     {

                                               Long id=Long.valueOf(strid);

                                               FileNodeDetailVo fileNode = fileDirLinkService.getFileSystemNodeByDirId(null, null,id,null, null);                                    

                                               FileNodeVo rFileNodeVo  = new FileNodeVo();

                                               changeToRFileNodeVo(fileNode, rFileNodeVo);

                                               rFileNodeVoList.add(rFileNodeVo);

                                     }

                            }

                            if(fileId!=null&&fileId.length()>0)

                            {

                                     String[] fileids=fileId.split(",");

                                     for(String strid:fileids)

                                     {

                                               Long id=Long.valueOf(strid);

                                               FileVo  fileVo = fileService.getFileByFileId(id);

                                               if(fileVo!=null)

                                               {

                                                        FileNodeVo rFileNodeVo=new FileNodeVo();

                                                        changeToRFileNodeVo(fileVo,rFileNodeVo);

                                                        rFileNodeVoList.add(rFileNodeVo);

                                               }

                                              

                                     }

                            }                

        

                   }catch(Exception e){

                            logger.error(e.getMessage());

                            return error(500,e.getMessage());

                   }

                   return response(rFileNodeVoList);

         }

        

         /**

         * 将fileVo转成rFileNodeVo，真麻烦，就不能将所有文件对象vo统一？

         * @param fileNode

         * @param rFileNodeVo

         */

         private void changeToRFileNodeVo(FileVo fileNode,FileNodeVo rFileNodeVo) {              

                   rFileNodeVo.setFileId(fileNode.getFileId());

                   rFileNodeVo.setFId(fileNode.getFId());

                   rFileNodeVo.setContentType(fileNode.getContentType());

                   rFileNodeVo.setFileName(fileNode.getFileRealName());

                   rFileNodeVo.setFileSize(fileNode.getFileSize());

                   rFileNodeVo.setCreationDate(fileNode.getCreationDate());

                   rFileNodeVo.setCreatedBy(fileNode.getCreatedBy());

                   rFileNodeVo.setCreatorName(fileNode.getCreatorName());

                   rFileNodeVo.setLastUpdateDate(fileNode.getLastUpdateDate());

                   rFileNodeVo.setModifiorName(fileNode.getModifiorName());

                   rFileNodeVo.setLastUpdatedBy(fileNode.getLastUpdatedBy());

         }

        

         /**

         * 根据已经删除掉的文件版本id，取得没有被删除掉的文件版本id

         * @param allFileLis

         * @param deleteFileLis

         * @return

         */

         private List<String> getHaveNotDeleteFiles(List<String> allFileLis,List<String> deleteFileLis){

                   List<String> havaNotDeleteFileLis = new ArrayList<String>();

                   for(String str : allFileLis){

                            if(!deleteFileLis.contains(str)){

                                     havaNotDeleteFileLis.add(str);

                            }

                   }

                   return havaNotDeleteFileLis;

         }

 

}
