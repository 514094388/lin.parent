package lin.file.controller;

 

import java.io.File;

import java.io.FileInputStream;

import java.io.FileOutputStream;

import java.io.IOException;

import java.io.InputStream;

import java.util.ArrayList;

import java.util.Date;

import java.util.HashMap;

import java.util.List;

import java.util.Set;

import java.util.zip.ZipEntry;

import java.util.zip.ZipOutputStream;

 

import javax.servlet.ServletOutputStream;

import javax.servlet.http.HttpServletResponse;

 

import org.apache.commons.io.FileUtils;

import org.apache.commons.io.IOUtils;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.multipart.MultipartFile;

 

import lin.core.common.AbstractControllerBase;

import lin.core.common.ResponseVo;

import lin.core.mvc.service.IUUIDService;

 

import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;

import io.swagger.annotations.ApiParam;

import lin.file.constants.FileConstants;

import lin.file.service.IFileDirLinkService;

import lin.file.service.IFileDirService;

import lin.file.service.IFileService;

import lin.file.vo.FileDirVo;

import lin.file.vo.FileNodeDetailVo;

import lin.file.vo.FileVo;

 

/**

* @author zwx406636

*/

@Api(value="文件上传",produces=MediaType.APPLICATION_JSON_VALUE)

@RestController

@RequestMapping("/api/file/file")

public class FileController extends AbstractControllerBase {

        

         private static Logger logger = Logger.getLogger(FileController.class);

 

        

         @Value("${uploadPath}")

         private String uploadPath;

        

        

         @Autowired

         private IFileService fileService;

        

         @Autowired

         IUUIDService uUIDService;

        

         @Autowired

         IFileDirService fileDirService;

        

         @Autowired

         IFileDirLinkService fileDirLinkService;

        

 

         private String prefixDir(){

                   String timeNow = new Date().getTime()+"";

                   return timeNow.substring(0, timeNow.length()-8);

         }

        

        

         @ApiOperation("上传")

         @PostMapping("/upload")

         @ResponseBody

         public ResponseVo<FileVo> upload(

                            @ApiParam(value = "上传的文件", required = true)

                            @RequestParam("file") MultipartFile  file,

                            @ApiParam(value = "objectId", required = true)

                            @RequestParam("objectId") String objectId,

                            @ApiParam(value = "objectType", required = true)

                            @RequestParam("objectType") String objectType,

                            @ApiParam(value = "dirId", required = false)

                            @RequestParam(value="dirId",required= false) Long dirId,

                            @ApiParam(value = "fId", required = false)

                            @RequestParam(value="fId",required= false) Long fId){

                   FileVo fileVo = null;

                   try{

 

                            if(file.isEmpty()){

                                     return error(1200,"文件为空");

                            }

                           

                            long fileSize = file.getSize();

                            String contentType = file.getContentType();

//                         String name = file.getName();

                            String originalName = file.getOriginalFilename();

                           

                            int dot = originalName.lastIndexOf(".");

                            String fixFileType ="";

                            if(dot > -1){

                                       fixFileType = originalName.substring(dot, originalName.length());

                            }

                           

                            String prefixDir = prefixDir();

                            String fixDir =  prefixDir+"/";

                           

                            long uuid = uUIDService.generateKey();

                           

                            String fixfilePath = fixDir+uuid+fixFileType;

                           

                            try {

                                    

                                     FileUtils.forceMkdir(new File(uploadPath+fixDir));

                                    

                                     File outFile = new File(uploadPath+fixfilePath);

                                    

                                     InputStream in = file.getInputStream();

                                    

                                     FileUtils.copyInputStreamToFile(in, outFile);

                            } catch (IOException e) {

                                     // TODO Auto-generated catch block

                                     logger.error(e.getMessage());

                                     return error(3100,"创建文件失败");

                            }

                           

                            fileVo = new FileVo();

                            fileVo.setObjectId(objectId);

                            fileVo.setObjectType(objectType);

                           

                            if(fId !=null && fId > 0){

                                     FileVo pFileVo = fileService.getFileByFid(fId);

                                     if(pFileVo== null || pFileVo.getFId() == null){

                                               return error(3101,"fId 不存在");

                                     }

                                    

                                     Long version = pFileVo.getVersion();

                                    

                                     fileVo.setFId(pFileVo.getFId());

                                     fileVo.setVersion(++version);

                                    

                            }else{

                                     fileVo.setFId(uuid);

                                     fileVo.setVersion(1l);

                            }

                  

                            fileVo.setContentType(contentType);

                            fileVo.setFileType(fixFileType);

                            fileVo.setFileSize(fileSize);

                            fileVo.setFileRealName(originalName);

                            fileVo.setFilePath(fixfilePath);

 

                            fileVo.setFileDir(fixDir);

                            fileVo.setIsDelete(false);

                           

                            fileService.createFile(fileVo);

                           

                           

                            if(dirId != null){

                                     if(objectType.equals(FileConstants.UPLOAD_GROUP_TYPE) || objectType.equals(FileConstants.UPLOAD_USER_TYPE)){

                                               fileDirService.addFIdToDir(fileVo.getFId(), dirId, objectType, objectId);

                                               fileDirService.updateFileSizeByObjectAndDirId(objectType, objectId, dirId);

                                     }

                            }

                   }catch(Exception e){

                            logger.error(e.getMessage());

                            return error(500,e.getMessage());

                   }

                   return response(fileVo);

         }

        

        

         @ApiOperation("查询文件的历史记录")

         @GetMapping("/getFileByFId")

         @ResponseBody

         public ResponseVo<List<FileVo>> getFileByFId(

                            @ApiParam(value = "fId")

                            @RequestParam(value="fId") Long fId){

                   List<FileVo> fileVoList = null;

                   try{

                            fileVoList =        fileService.getFileListByFid(fId);

                   }catch(Exception e){

                            logger.error(e.getMessage());

                            return error(500,e.getMessage());

                   }

                   return response(fileVoList);

         }

        

         @ApiOperation("根据具体文件id查询文件信息")

         @GetMapping("/getFileByFileId")

         @ResponseBody

         public ResponseVo<FileVo> getFileByFileId(

                            @ApiParam(value = "fileId")

                            @RequestParam(value="fileId") Long fileId){

                   FileVo fileVo =  null;

                   try{

                            fileVo = fileService.getFileByFileId(fileId);

                   }catch(Exception e){

                            logger.error(e.getMessage());

                            return error(500,e.getMessage());

                   }

                   return response(fileVo);

         }

        

        

        

         @ApiOperation("更改文件的相关信息")

         @PostMapping("/updateFileByFileId")

         @ResponseBody

         public ResponseVo<FileVo> updateFileByFileId(

                            @ApiParam(value = "fileVo")

                            @RequestBody FileVo fileVo){

                   try{

                            fileService.updateFileByFileId(fileVo);

                   }catch(Exception e){

                            logger.error(e.getMessage());

                            return error(500,e.getMessage());

                   }

                   return response(fileVo);

         }

        

        

         @ApiOperation("批量下载文档")

         @GetMapping("/downLoadFiles")

         @ResponseBody

         public void downLoadFiles(

                            @ApiParam(value = "文件id，有多个值中间用逗号分隔", required = false)

                            @RequestParam("fileId") String fileId,

                            @ApiParam(value = "文件夹id，有多个值中间用逗号分隔", required = false)

                            @RequestParam("dirId") String dirId,

                            HttpServletResponse response){

                   ServletOutputStream out = null;

                   try {

                            out = response.getOutputStream();

                            if((fileId==null||fileId.length()==0)&&(dirId==null||dirId.length()==0))

                            {

                                     out.print(errorToJson(600,"fileId is null."));

                            }

                            List<Long> listfileId=new ArrayList<Long>();

                            List<Long> listDirId=new ArrayList<Long>();

                            if(fileId!=null&&fileId.length()>0)

                            {

                                     String[] fileids=fileId.split(",");

                                     for(String id:fileids)

                                     {

                                               listfileId.add(Long.valueOf(id));

                                     }                          

                            }

                            if(dirId!=null&&dirId.length()>0)

                            {

                                     String[] dirids=dirId.split(",");

                                     for(String id:dirids)

                                     {

                                               listDirId.add(Long.valueOf(id));

                                     }

                            }

                            //获取到打包文件，文件夹路径，文件真实名称及文件真实路径的关系map

                            HashMap<String,List<HashMap<String,String>>> hspath=getFilePathMap(listfileId,listDirId);

                            Date nowtime=new Date();

                            //打包目录

                            String zipfolderPath=uploadPath.substring(0,uploadPath.lastIndexOf("/"))+"/zipFolder";

                            File zipfolder=new File(zipfolderPath);

                            if(!zipfolder.exists()||!zipfolder.isDirectory())

                            {

                                     zipfolder.mkdir();

                            }

                            //打包文件

                            String zippath=zipfolderPath+"/files_"+nowtime.getTime()+".zip";

                            zipMultiFiles(hspath,zippath);

                            File file = new File(zippath);

                            FileInputStream fileIn = new FileInputStream(file);

                            IOUtils.copy(fileIn, out);                   

                            out.close();

                            fileIn.close();

                   }catch(Exception e){                           

                            try {

                                     out.print(errorToJson(500,e.getMessage()));

                            } catch (IOException e1) {

                                     logger.error(e1.getMessage());

                            }

                            logger.error(e.getMessage());

                   }

         }

        

         /**

         * 得到文件和文件路径关系

         * @param rFileNodeVoList

         * @return

         */

         private HashMap<String,List<HashMap<String,String>>> getFilePathMap( List<Long> listFileId,List<Long> listDirId)

         {

                   HashMap<String,List<HashMap<String,String>>> hspath=new HashMap<String,List<HashMap<String,String>>>();

                   //获取到第一层文件夹下的文件

                   List<HashMap<String,String>> listfile=hspath.get("");

                   if(listFileId!=null&&listFileId.size()>0)

                   {

                            for(Long fileid:listFileId)

                            {

                                     FileVo fileVo =  fileService.getFileByFileId(fileid);

                                     if(fileVo!=null)

                                     {

                                               String filepath=uploadPath+"/"+fileVo.getFilePath();

                                               String realfilename=fileVo.getFileRealName();

                                               HashMap<String,String> hsfileinfo=new HashMap<String,String>();

                                               hsfileinfo.put("filepath", filepath);

                                               hsfileinfo.put("filename", realfilename);

                                              

                                               if(listfile!=null)

                                               {

                                                        listfile.add(hsfileinfo);

                                               }else

                                               {

                                                        listfile=new ArrayList<HashMap<String,String>>();

                                                        listfile.add(hsfileinfo);

                                                        hspath.put("", listfile);

                                               }

                                     }

                            }

                   }

                   if(listDirId!=null&&listDirId.size()>0)

                   {

                            for(Long dirId:listDirId)

                            {

                                     //获取到该文件夹

                                     FileDirVo dir=fileDirService.getById(dirId);

                                     getFilePathMapByDir(hspath,dir,"");

                            }

                           

                   }

                  

                   return hspath;

         }

        

         /**

         * 递归遍历文件夹下的子项

         * @param hspath

         * @param fileVo

         */

         private void getFilePathMapByDir(HashMap<String,List<HashMap<String,String>>> hspath,FileDirVo dir,String dirpath)

         {                

                   //获取到该文件夹

                   if("".equals(dirpath))

                   {

                            dirpath=dir.getDirName()+"/";

                   }else

                   {

                            dirpath=dirpath+"/"+dir.getDirName()+"/";

                   }

                  

                   //获取到文件夹下所有文件

                   Long dirId=dir.getDirId();

                   List<FileNodeDetailVo> files=fileDirLinkService.getFileNodeListByDirId(dirId);

                   List<HashMap<String,String>> listfile=hspath.get(dirpath);

                  

                   for(FileNodeDetailVo file:files)

                   {

                            String realfilename=file.getFileRealName();

                            String filepath=uploadPath+file.getFilePath();

                            HashMap<String,String> hsfileinfo=new HashMap<String,String>();

                            hsfileinfo.put("filepath", filepath);

                            hsfileinfo.put("filename", realfilename);

                            if(listfile==null)

                            {

                                     listfile=new ArrayList<HashMap<String,String>>();

                                     hspath.put(dirpath, listfile);

                            }

                            listfile.add(hsfileinfo);

                   }

                   //获取到该文件夹下的所有文件夹

                   List<FileDirVo> subdirs=fileDirService.getListByParentId(dirId);

                   for(FileDirVo subdir:subdirs)

                   {

                            getFilePathMapByDir(hspath,subdir,dirpath);

                   }

                  

         }

        

         /**

         * 对一系列文件进行压缩

         * @param hashpath 记录文件,真实文件名和文件夹关系

         * @param zippath 目标压缩文件路径

         */

         private void zipMultiFiles(HashMap<String,List<HashMap<String,String>>> hspath, String zippath) {

                   try {

                            File zipFile = new File(zippath);

                            ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(

                                               zipFile));

                            Set<String> paths=hspath.keySet();

                            for(String folderpath:paths)

                            {

                                     List<HashMap<String,String>> filepaths=hspath.get(folderpath);

                                     for(HashMap<String,String> hsfilepath:filepaths)

                                     {

                                               String filepath=hsfilepath.get("filepath");

                                               String realName=hsfilepath.get("filename");

                                               File file = new File(filepath);

                                               if (file.exists()) {

                                                        zipFile(zipOut, file, folderpath,realName);

                                               }

                                     }

                            }                          

                            zipOut.close();

                   } catch (Exception e) {

                            logger.error(e.getMessage());

                   }

         }

        

         /**

         * 压缩文件

         * @param zipOut zip文件io流

         * @param file 文档对象

         * @param baseDir 基础文件位置

         * @param realName 真实文件名（数据库中记录的）

         */

         private void zipFile(ZipOutputStream zipOut, File file,

                            String baseDir,String realName) {

                   InputStream input=null;

                   try

                   {

                            byte[] buf = new byte[1024];

                            input = new FileInputStream(file);

                            zipOut.putNextEntry(new ZipEntry(baseDir + realName));                      

                            int len;

                            while ((len = input.read(buf)) != -1) {

                                     zipOut.write(buf, 0, len);

                            }

                            input.close();

                            input=null;

                   } catch (IOException e) {

                            // TODO Auto-generated catch block

                            logger.error(e.getMessage());

                   }finally

                   {

                            if(input!=null)

                            {

                                     try {

                                               input.close();

                                     } catch (IOException e) {

                                               // TODO Auto-generated catch block

                                               logger.error(e.getMessage());

                                     }

                            }

                   }

                  

         }

        

        

//      @ApiOperation("测试是否有权限创建文件夹，不要使用")

//      @GetMapping("/testCreateFolder")

//      @ResponseBody

//      public void testCreateFolder(

//                         @ApiParam(value = "foldername", required = false)

//                         @RequestParam("foldername") String foldername

//                         ){

//              

//               String testfolderPath=uploadPath.substring(0,uploadPath.lastIndexOf("/"))+"/"+foldername;

//               File testfolder=new File(testfolderPath);

//               if(!testfolder.exists()||!testfolder.isDirectory())

//               {

//                         testfolder.mkdir();

//               }

//              

//               File testfolder1=new File(testfolderPath);

//              

//      }

 

}
