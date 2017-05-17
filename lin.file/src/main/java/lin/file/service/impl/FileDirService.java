package lin.file.service.impl;

 

import java.util.Date;

import java.util.List;

 

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

 

import lin.core.common.AbstractServiceBase;

import lin.core.mvc.service.IUUIDService;

import lin.core.user.service.IUserService;

 

import lin.file.dao.IFileDirDao;

import lin.file.service.IFileDirLinkService;

import lin.file.service.IFileDirService;

import lin.file.service.IFileService;

import lin.file.service.IRecycleService;

import lin.file.vo.FileDirLinkVo;

import lin.file.vo.FileDirVo;

import lin.file.vo.FileNodeDetailVo;

import lin.file.vo.FileVo;

import lin.file.vo.RecycleVo;

 

 

@Service("fileDirService")

public class FileDirService extends AbstractServiceBase implements IFileDirService {

 

         @Autowired

         IFileDirDao      iFileDirDao;

        

         @Autowired

         IFileDirLinkService fileDirLinkService;

        

        

         @Autowired

         private IFileService fileService;

        

         @Autowired

         private IRecycleService recycleService;

        

        

         @Autowired

         private IUUIDService uUIDService;

        

         @Autowired

         private IUserService userService;

        

         public Long addFileDir(FileDirVo fileDirVo){

                   if(fileDirVo.getCreatedBy() == null){

                            fileDirVo.setCreatedBy(userService.getSessionUserInfoVo().getId());

                   }

                   return iFileDirDao.addFileDir(fileDirVo);

         }

        

         public Long updateFileDir(FileDirVo fileDirVo){

                   if(fileDirVo.getLastUpdatedBy() == null){

                            fileDirVo.setLastUpdatedBy(userService.getSessionUserInfoVo().getId());

                   }

                   return iFileDirDao.updateFileDir(fileDirVo);

         }

        

         public FileDirVo getById(Long dirId){

                   return iFileDirDao.getById(dirId);

         }

        

         public List<FileDirVo> getListByParentIdAndObject(FileDirVo fileDirVo){

                   return iFileDirDao.getListByParentIdAndObject(fileDirVo);

         }

        

         public Long delByDirId(FileDirVo fileDirVo){

                   fileDirVo.setLastUpdatedBy(userService.getSessionUserInfoVo().getId());

                   return iFileDirDao.delByDirId(fileDirVo);

         }

        

        

        

        

         public FileDirVo getRootDirByObject(String objectType,String objectId){

                   FileDirVo rootFileDirVo = iFileDirDao.getRootDirByObject(objectType,objectId);

                   if(rootFileDirVo == null){

                            FileDirVo fileDirVo = new FileDirVo();

                            fileDirVo.setParentId(0l);

                            fileDirVo.setObjectType(objectType);

                            fileDirVo.setObjectId(objectId);

                            fileDirVo.setDirSize(0l);

                            fileDirVo.setCreatedBy(userService.getSessionUserInfoVo().getId());

                            fileDirVo.setIsDelete(false);

                            addFileDir(fileDirVo);

                            return fileDirVo;

                   }

                   return rootFileDirVo;

         }

        

//      public void uploadFileSizeByObjectAndDirId(String objectType,String objectId, Long dirId){

//              

//               if(dirId == 0){

//                         EUIFileDir euiFileDir = getRootDirByObject(objectType,objectId);

//                         dirId = euiFileDir.getDirId();

//               }

//              

//              

//               EUIFileDir euiFileDir =      getById(dirId);

//               if(euiFileDir == null){

//                         return ;

//               }

//               List<EUIFile> fileList = iFileDirLinkService.getFileListByDirId(dirId);

//               Long fileSize = 0l;

//               for (EUIFile euiFile : fileList) {

//                         Long fileSize1 =  euiFile.getFileSize() == null ? 0 :euiFile.getFileSize();

//                         fileSize += fileSize1;

//               }

//              

//               EUIFileDir tmpEuiFileDir = new EUIFileDir();

//               tmpEuiFileDir.setParentId(dirId);

//               tmpEuiFileDir.setObjectType(objectType);

//               tmpEuiFileDir.setObjectId(objectId);

//              

//               List<EUIFileDir> dirLsit = getListByParentIdAndObject(tmpEuiFileDir);

//               Long dirSize = 0l;

//               for (EUIFileDir euiFileDir2 : dirLsit) {

//                        

//                         Long dirSize1 =  euiFileDir2.getDirSize() == null ? 0 :euiFileDir2.getDirSize();

//                         dirSize += dirSize1;

//                        

//               }

//               euiFileDir.setDirSize(fileSize+dirSize);

//              

//               updateEUIFileDir(euiFileDir);

//              

//               Long parentId = euiFileDir.getParentId();

//              

//              

//               if(parentId != 0){

//                         uploadFileSizeByObjectAndDirId(objectType,objectId, parentId);

//               }

//              

//      }

        

        

         public void addFIdToDir(Long fId, Long dirId,String objectType,String objectId){

                  

                   if(dirId == 0){

                            FileDirVo fileDirVo = getRootDirByObject(objectType, objectId);

                            dirId = fileDirVo.getDirId();

                   }

                   FileDirLinkVo fileDirLinkVo = fileDirLinkService.getByfId(fId);

                   if(fileDirLinkVo == null){

                            fileDirLinkVo = new FileDirLinkVo();

                            fileDirLinkVo.setDirId(dirId);

                            fileDirLinkVo.setFId(fId);

                            fileDirLinkService.addFileDirLink(fileDirLinkVo);

                   }else{

                            fileDirLinkVo.setDirId(dirId);

                            fileDirLinkService.updateFileDirLink(fileDirLinkVo);

                   }

         }

        

        

//      public void delByDirIdAndObject(EUIFileDir euiFileDir){

//               Long dirId = euiFileDir.getDirId();

//               String objectType = euiFileDir.getObjectType();

//               String objectId = euiFileDir.getObjectId();

//              

//               EUIFileDir delEuiFileDir = new EUIFileDir();

//               delEuiFileDir.setParentId(dirId);

//              

//               delEuiFileDir.setObjectId(objectId);

//               delEuiFileDir.setObjectType(objectType);

//              

//               List<EUIFileDir> delEuiFileDirList = getListByParentIdAndObject(delEuiFileDir);

//               for (EUIFileDir delEuiFileDir1 : delEuiFileDirList) {

//                        

//                         delByDirIdAndObject(delEuiFileDir1);

//                         Long delDirId = delEuiFileDir1.getDirId();

//                         List<EUIFile> fileList = iFileDirLinkService.getFileListByDirId(delDirId);

//                        

//                         for (EUIFile euiFile : fileList) {

//                                  iFileService.delByFId(euiFile);

//                         }

//                        

//                         List<EUIFileDirLink> fileLinkList = iFileDirLinkService.getListByDirId(delDirId);

//                         for (EUIFileDirLink euiFileDirLink : fileLinkList) {

//                                  iFileDirLinkService.delByFDirId(euiFileDirLink);

//                         }

//                        

//                        

//               }

//              

//      }

        

         public void updateFileSizeByObjectAndDirId(String objectType,String objectId, Long dirId){

                  

//               FileNode dirNode = new FileNode();

                  

                   FileNodeDetailVo dirFileNodeDetailVo = fileDirLinkService.getFileSystemNodeByDirId(objectType, objectId, dirId);

                   List<FileNodeDetailVo> childFileNodeDetailVoList = dirFileNodeDetailVo.getChildNode();

                  

                   Long fileSize = 0l;

                  

                   for (FileNodeDetailVo fileNodeDetailVo : childFileNodeDetailVoList) {

                           

                            String contentType = fileNodeDetailVo.getContentType();

                           

                            if(contentType == null || contentType.equals("")){

                           

                                     Long childDirId = fileNodeDetailVo.getDirId();

                                    

                                     updateFileSizeByObjectAndDirId(objectType, objectId, childDirId);

                                    

//                                  iFileDirLinkService.getFileSystemNodeByDirId(objectType, objectId, childDirId);

                                    

                                     FileDirVo fileDirVo = getById(fileNodeDetailVo.getDirId());

                                    

                                     fileSize += fileDirVo.getDirSize();

                                    

                            }else{

                                     Long fileNodeSize = fileNodeDetailVo.getFileSize() == null ? 0:fileNodeDetailVo.getFileSize();

                                     fileSize += fileNodeSize;

                            }

                           

                   }

                  

                   Long dirSize = dirFileNodeDetailVo.getDirSize();

                  

                   if(dirSize != fileSize){

                            FileDirVo fileDirVo = new FileDirVo();

                            fileDirVo.setDirId(dirFileNodeDetailVo.getDirId());

                            fileDirVo.setDirSize(fileSize);

                            updateFileDir(fileDirVo);

                   }

        

         }

        

        

        

         private void getDirAndFileWithHistoryByFileNode(FileNodeDetailVo fileNodeDetailVo) {

                  

                   FileNodeDetailVo dirFileNodeDetailVo = fileDirLinkService.getFileSystemNodeWithHistoryByDirId(fileNodeDetailVo.getObjectType(), fileNodeDetailVo.getObjectId(), fileNodeDetailVo.getDirId(), null);

                   List<FileNodeDetailVo> childFileNodeDetailVoList = dirFileNodeDetailVo.getChildNode();

                  

                   if(childFileNodeDetailVoList == null){

                            return;

                   }

                  

                   for (FileNodeDetailVo FileNodeDetailVo1 : childFileNodeDetailVoList) {

                            String contentType = fileNodeDetailVo.getContentType();

                            if(contentType == null || contentType.equals("")){

                                     getDirAndFileWithHistoryByFileNode(FileNodeDetailVo1);

                            }

                   }

                   fileNodeDetailVo.setChildNode(childFileNodeDetailVoList);

         }

        

        

         private void getDirAndFileByFileNode(FileNodeDetailVo fileNodeDetailVo) {

                  

                   FileNodeDetailVo dirFileNodeDetailVo = fileDirLinkService.getFileSystemNodeByDirId(fileNodeDetailVo.getObjectType(), fileNodeDetailVo.getObjectId(), fileNodeDetailVo.getDirId());

                   List<FileNodeDetailVo> childFileNodeDetailVoList = dirFileNodeDetailVo.getChildNode();

                  

                   if(childFileNodeDetailVoList == null){

                            return;

                   }

                  

                   for (FileNodeDetailVo fileNodeDetailVo1 : childFileNodeDetailVoList) {

                            String contentType = fileNodeDetailVo.getContentType();

                            if(contentType == null || contentType.equals("")){

                                     getDirAndFileByFileNode(fileNodeDetailVo1);

                            }

                   }

                   fileNodeDetailVo.setChildNode(childFileNodeDetailVoList);

         }

        

        

        

        

         public void delDirAndFileByFileNode(FileNodeDetailVo dirFileNodeDetailVo){

                   Long dirId = dirFileNodeDetailVo.getDirId();

                   FileDirVo fileDirVo = new FileDirVo();

                   fileDirVo.setDirId(dirId);

                   delByDirId(fileDirVo);

                   List<FileNodeDetailVo> childFileNodeDetailVoList = dirFileNodeDetailVo.getChildNode();

                   if(childFileNodeDetailVoList != null){

                            for (FileNodeDetailVo fileNodeDetailVo : childFileNodeDetailVoList) {

                                     String contentType = fileNodeDetailVo.getContentType();

                                     if(contentType == null || contentType.equals("")){

                                               delDirAndFileByFileNode(fileNodeDetailVo);

                                               FileDirLinkVo fileDirLinkVo = new FileDirLinkVo();

                                               fileDirLinkVo.setFDirId(fileNodeDetailVo.getFDirId());

                                               fileDirLinkService.delByFDirId(fileDirLinkVo);

                                     }

                            }

                   }

                  

                  

                   List<FileNodeDetailVo> historyFileNodeDetailVoList = dirFileNodeDetailVo.getHistoryList();

                   if(historyFileNodeDetailVoList != null ){

                            for (FileNodeDetailVo historyFileNodeDetailVo : historyFileNodeDetailVoList) {

                                     FileVo fileVo = new FileVo();

                                     fileVo.setFileId(historyFileNodeDetailVo.getFileId());

                                     fileService.delByFileId(fileVo);

                            }

                           

                   }

                  

                  

         }

        

        

 

         public FileNodeDetailVo delDirAndFileByObjectAndDirId(String objectType, String objectId, Long dirId){

                   FileNodeDetailVo dirFileNodeDetailVo = getAllInfoByDirId(objectType, objectId, dirId);

                  

                   RecycleVo recycleVo = new RecycleVo();

                   recycleVo.setObjectId(Long.valueOf(objectId));

                   recycleVo.setObjectType(Long.valueOf(objectType));

                   //recycleVo.setRecycleInfo(JSON.toJSONString(dirFileNodeDetailVo));

                   recycleService.createRecycle(recycleVo);

                  

                  

                   delDirAndFileByFileNode(dirFileNodeDetailVo);

                   return dirFileNodeDetailVo;

         }

        

        

         public FileNodeDetailVo getAllInfoByDirId(String objectType, String objectId, Long dirId){

                   FileNodeDetailVo dirFileNodeDetailVo = fileDirLinkService.getFileSystemNodeWithHistoryByDirId(objectType, objectId, dirId, null);

                  List<FileNodeDetailVo> childFileNodeDetailVoList = dirFileNodeDetailVo.getChildNode();

                  

                   if(childFileNodeDetailVoList == null){

                            return null;

                   }

                  

                   for (FileNodeDetailVo fileNodeDetailVo : childFileNodeDetailVoList) {

                            String contentType = fileNodeDetailVo.getContentType();

                            if(contentType == null || contentType.equals("")){

                                     getDirAndFileWithHistoryByFileNode(fileNodeDetailVo);

                            }

                   }

                  

                   return dirFileNodeDetailVo;

         }

        

         public void copyFileDir(FileDirVo fileDirVo, Long toDirId){

                   String objectId = fileDirVo.getObjectId();

                   String objectType = fileDirVo.getObjectType();

                   Long dirId = fileDirVo.getDirId();

                  

                   FileNodeDetailVo dirFileNodeDetailVo = fileDirLinkService.getFileSystemNodeByDirId(objectType, objectId, dirId);

                   getDirAndFileByFileNode(dirFileNodeDetailVo);

                   handleCopyByFileNode(dirFileNodeDetailVo, toDirId);

         }

        

         private void handleCopyByFileNode(FileNodeDetailVo fileNodeDetailVo,Long toDirId) {

                  

                   String contentType = fileNodeDetailVo.getContentType();

                   if(contentType == null || contentType.equals("")){

                            FileDirVo fileDirVo = changeClass(fileNodeDetailVo, FileDirVo.class);

                            fileDirVo.setParentId(toDirId);

                            addFileDir(fileDirVo);

                           

                           

                            List<FileNodeDetailVo> childFileNodeDetailVoList = fileNodeDetailVo.getChildNode();

                           

                            if(childFileNodeDetailVoList != null){

                                     for (FileNodeDetailVo childFileNodeDetailVo : childFileNodeDetailVoList) {

                                              

                                               String childContentType = childFileNodeDetailVo.getContentType();

                                               if(childContentType == null || childContentType.equals("")){

                                                        handleCopyByFileNode(childFileNodeDetailVo, fileDirVo.getDirId());

                                               }else{

                                                        FileVo fileVo = changeClass(fileNodeDetailVo, FileVo.class);

                                                        Long newFid = uUIDService.generateKey();

                                                        fileVo.setFId(newFid);

                                                        fileVo.setCreatedBy(fileVo.getLastUpdatedBy() == null ? fileVo.getCreatedBy() :fileVo.getLastUpdatedBy());

                                                        fileService.createFile(fileVo);

                                                        FileDirLinkVo fileDirLinkVo = new FileDirLinkVo();

                                                        fileDirLinkVo.setDirId(fileDirVo.getDirId());

                                                        fileDirLinkVo.setFId(newFid);

                                                        fileDirLinkVo.setCreatedBy(fileVo.getLastUpdatedBy() == null ? fileVo.getCreatedBy() :fileVo.getLastUpdatedBy());

                                                        fileDirLinkService.addFileDirLink(fileDirLinkVo);

                                               }

                                     }

                            }

                   }else{

                           

                            FileVo fileVo = changeClass(fileNodeDetailVo, FileVo.class);

                            Long newFid = uUIDService.generateKey();

                            fileVo.setFId(newFid);

                            fileVo.setCreatedBy(fileVo.getLastUpdatedBy() == null ? fileVo.getCreatedBy() :fileVo.getLastUpdatedBy());

                            fileService.createFile(fileVo);

                           

                            FileDirLinkVo fileDirLinkVo = new FileDirLinkVo();

                            fileDirLinkVo.setDirId(toDirId);

                            fileDirLinkVo.setFId(newFid);

                            fileDirLinkVo.setCreatedBy(fileVo.getLastUpdatedBy() == null ? fileVo.getCreatedBy() :fileVo.getLastUpdatedBy());

                            fileDirLinkService.addFileDirLink(fileDirLinkVo);

                   }

 

         }

        

         public List<FileDirVo> getListByParentId(Long parentId){

                   return iFileDirDao.getListByParentId(parentId);

         }

 

         @Override

         public void updateDirAndSaveDirInfo(FileDirVo fileDirVo, Date updateDate) {

                   if(fileDirVo.getParentId()==0L){

                            return;

                   }

                   RecycleVo recycleVo = new RecycleVo();

                   recycleVo.setObjectId(fileDirVo.getDirId());

                   recycleVo.setParentId(fileDirVo.getParentId());

                   recycleVo.setIsDelete(0L);

                   recycleVo.setObjectType(1L);

                   recycleVo.setLastUpdateDate(updateDate);

                   recycleVo.setLastUpdatedBy(userService.getSessionUserInfoVo().getId());

                   recycleService.createRecycle(recycleVo);

                  

                   fileDirVo.setParentId(0L);

                   iFileDirDao.updateFileDir(fileDirVo);

         }

 

         @Override

         public void recycleFileDir(RecycleVo recycle) {

                   FileDirVo fileDirVo = this.getById(recycle.getObjectId());

                   if(fileDirVo!=null){

                            fileDirVo.setParentId(recycle.getParentId());

                            this.updateFileDir(fileDirVo);

                           

                            recycle.setIsDelete(1L);

                            recycleService.updateRecycle(recycle);

                   }

         }

 

        

}

 

 

 
