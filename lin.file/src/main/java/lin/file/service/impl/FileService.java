package lin.file.service.impl;

 

import java.util.ArrayList;

import java.util.Date;

import java.util.List;

 

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

 

import lin.core.common.AbstractServiceBase;

import lin.core.mvc.dao.IDBDao;

import lin.core.user.service.IUserService;

 

import lin.file.dao.IFileDao;

import lin.file.service.IFileService;

import lin.file.service.IRecycleService;

import lin.file.vo.FileVo;

import lin.file.vo.RecycleVo;

 

@Service("fileService")

public class FileService extends AbstractServiceBase implements IFileService {

 

         @Autowired

         IFileDao iFileDao;

        

         @Autowired

         IDBDao   iDBDao;

        

         @Autowired

         private IUserService userService;

        

         @Autowired

         private IRecycleService recycleService;

        

         public Long createFile(FileVo fileVo) {

                   if(fileVo.getCreatedBy() == null){

                            fileVo.setCreatedBy(userService.getSessionUserInfoVo().getId());

                   }

                   return iFileDao.createFile(fileVo);

         }

         public Long updateFileByFileId(FileVo fileVo){

                   if(fileVo.getLastUpdatedBy() == null){

                            fileVo.setLastUpdatedBy(userService.getSessionUserInfoVo().getId());

                   }

                   return iFileDao.updateFileByFileId(fileVo);

         }

        

         public FileVo getFileByFid(Long fId){

                   return iFileDao.getFileByFid(fId);

         }

 

 

         public FileVo getFileByFileId(Long fileId){

                   return iFileDao.getFileByFileId(fileId);

         }

        

         public List<FileVo> getFileListByFid(Long fId){

                   return iFileDao.getFileListByFid(fId);

         }

 

         public Long delByFId(FileVo fileVo) {

                   fileVo.setLastUpdatedBy(userService.getSessionUserInfoVo().getId());

                   return iFileDao.delByFId(fileVo);

         }

        

         public Long delByFileId(FileVo fileVo){

                  fileVo.setLastUpdatedBy(userService.getSessionUserInfoVo().getId());

                   return iFileDao.delByFileId(fileVo);

         }

         @Override

         public Long copyFile(Long fId) {

                   // TODO Auto-generated method stub

                   Long uid = userService.getSessionUserInfoVo().getId();

                   Long newFId = iDBDao.generateKey();

                   iFileDao.copyFile(fId,newFId,uid);

                   return newFId;

         }

         @Override

         public void recycleFile(RecycleVo recycle) {

                   FileVo fileVo = this.getFileByFileId(recycle.getObjectId());

                   if(fileVo!=null){

                            fileVo.setIsDelete(false);

                            this.updateFileByFileId(fileVo);

                           

                            recycle.setIsDelete(1L);

                            recycleService.updateRecycle(recycle);

                   }

         }

        

         @Override

         public List<String> updateFileAndSaveRecycleByFileId(List<String> fileDeleteList) {

                   List<String> deleteFileId = new ArrayList<String>();

                   for(String fileId :fileDeleteList){

                            if(fileId!=null){

                                     FileVo fileVo = this.getFileByFileId(Long.valueOf(fileId));

                                     if(fileVo!=null){

                                               if(fileVo.getIsDelete()==true)

                                                        continue;

                                               fileVo.setIsDelete(true);

                                               this.updateFileByFileId(fileVo);

                                              

                                               RecycleVo recycleVo = new RecycleVo();

                                               recycleVo.setObjectId(fileVo.getFileId());

                                               recycleVo.setIsDelete(0L);

                                               recycleVo.setObjectType(2L);

                                               recycleVo.setLastUpdateDate(new Date());

                                               recycleVo.setLastUpdatedBy(userService.getSessionUserInfoVo().getId());

                                               recycleService.createRecycle(recycleVo);

                                               deleteFileId.add(fileId);

                                     }

                            }

                   }

                   return deleteFileId;

         }

 

 

        

}
