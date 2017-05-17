package lin.file.service.impl;

 

import java.util.ArrayList;

import java.util.List;

 

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

 

import lin.core.common.AbstractServiceBase;

import lin.core.user.service.IUserService;

 

import lin.file.dao.IFileDirLinkDao;

import lin.file.service.IFileDirLinkService;

import lin.file.service.IFileDirService;

import lin.file.service.IFileService;

import lin.file.service.IRecycleService;

import lin.file.vo.FileDirLinkVo;

import lin.file.vo.FileDirVo;

import lin.file.vo.FileNodeDetailVo;

import lin.file.vo.FileNodeVo;

import lin.file.vo.FileVo;

import lin.file.vo.RecycleVo;

 

 

@Service("fileDirLinkService")

public class FileDirLinkService extends AbstractServiceBase implements IFileDirLinkService {

 

         @Autowired

         IFileDirLinkDao        iFileDirLinkDao;

        

         @Autowired

         private IFileService fileService;

        

         @Autowired

         private IFileDirService fileDirService;

        

         @Autowired

         private IUserService userService;

        

         @Autowired

         IRecycleService recycleService;

        

         public Long addFileDirLink(FileDirLinkVo fileDirLinkVo){

                   if(fileDirLinkVo.getCreatedBy() == null){

                            fileDirLinkVo.setCreatedBy(userService.getSessionUserInfoVo().getId());

                   }

                   return iFileDirLinkDao.addFileDirLink(fileDirLinkVo);

         }

        

         public Long updateFileDirLink(FileDirLinkVo fileDirLinkVo){

                   if(fileDirLinkVo.getLastUpdatedBy() == null){

                            fileDirLinkVo.setLastUpdatedBy(userService.getSessionUserInfoVo().getId());

                   }

                   return iFileDirLinkDao.updateFileDirLink(fileDirLinkVo);

         }

        

         public FileDirLinkVo getById(Long dirId){

                   return iFileDirLinkDao.getById(dirId);

         }

        

        

         public List<FileDirLinkVo> getListByDirId(Long dirId){

                   return iFileDirLinkDao.getListByDirId(dirId);

         }

        

//      public List<FileVo> getFileListByDirId(Long dirId){

//               List<FileVo> fileVoList = new ArrayList<FileVo>();

//               List<FileDirLinkVo> fileDirLinkVoList =  getListByDirId(dirId);

//               for (FileDirLinkVo fileDirLinkVo : fileDirLinkVoList) {

//                         FileVo fileVo = iFileService.getFileByFid(fileDirLinkVo.getFId());

//                         fileVoList.add(fileVo);

//                        

//               }

//               return fileVoList;

//      }

        

        

         public List<FileNodeDetailVo> getFileNodeListByDirId(Long dirId){

                   return getFileNodeListByDirId(dirId,null);

         }

        

         public List<FileNodeDetailVo> getFileNodeListByDirId(Long dirId,String srchText){

                   return iFileDirLinkDao.getFileNodeListByDirId(dirId,srchText);

         }

        

         public List<FileNodeDetailVo> getFileNodeWithHisToryListByDirId(Long dirId){

                   List<FileNodeDetailVo> fileNodeDetailVoList = getFileNodeListByDirId(dirId);

                  

                   for (FileNodeDetailVo fileNode : fileNodeDetailVoList) {

                            Long fId = fileNode.getFId();

                            List<FileVo> fileVoList = fileService.getFileListByFid(fId);

                            fileNode.setHistoryList(changeListClass(fileVoList, FileNodeDetailVo.class));

                   }

                  

                  

                   return fileNodeDetailVoList;

         }

        

        

         public FileDirLinkVo getByfId(Long fId){

                   return iFileDirLinkDao.getByfId(fId);

         }

        

        

         public List<FileDirLinkVo> getByDirId(Long dirId){

                   return iFileDirLinkDao.getByDirId(dirId);

         }

        

        

        

        

        

        

         public Long delByFDirId(FileDirLinkVo fileDirLinkVo){

                   fileDirLinkVo.setLastUpdatedBy(userService.getSessionUserInfoVo().getId());

                   return iFileDirLinkDao.delByFDirId(fileDirLinkVo);

         }

        

        

        

         public FileNodeDetailVo getFileSystemNodeWithHistoryByDirId(String objectType,String objectId,Long dirId,FileDirVo rootFileDirVo){

                            rootFile(objectType, objectId, dirId, rootFileDirVo);

                            FileNodeDetailVo fileNodeDetailVo = changeClass(rootFileDirVo, FileNodeDetailVo.class);

                            FileDirVo fileDirVo = new FileDirVo();

                            fileDirVo.setObjectId(objectId);

                            fileDirVo.setObjectType(objectType);

                            fileDirVo.setParentId(fileNodeDetailVo.getDirId());

                           

                            List<FileNodeDetailVo> childFileNodeDetailVoList = new ArrayList<FileNodeDetailVo>();

                           

                            List<FileDirVo> fileDirVoList = fileDirService.getListByParentIdAndObject(fileDirVo);

                           

                            List<FileNodeDetailVo> dirFileNodeDetailVoList = changeListClass(fileDirVoList, FileNodeDetailVo.class);

                           

                            childFileNodeDetailVoList.addAll(dirFileNodeDetailVoList);

                           

                            List<FileNodeDetailVo> fileFileNodeDetailVoList = getFileNodeWithHisToryListByDirId(dirId);

                           

                            childFileNodeDetailVoList.addAll(fileFileNodeDetailVoList);

                           

                            fileNodeDetailVo.setChildNode(childFileNodeDetailVoList);

                           

                            return fileNodeDetailVo;

         }

        

        

         public FileNodeDetailVo getFileSystemNodeByDirId(String objectType,String objectId,Long dirId, String srchText,FileDirVo rootFileDirVo ){

                  

//               BeanCopier fileDirCopier = BeanCopier.create(FileDirVo.class, FileNode.class, false);

 

                   rootFile(objectType, objectId, dirId, rootFileDirVo);

//                         fileDirCopier.copy(rootEUIFileDir,fileNode , null);

                   FileNodeDetailVo fileNodeDetailVo = changeClass(rootFileDirVo, FileNodeDetailVo.class);

                   FileDirVo fileDirVo = new FileDirVo();

                   fileDirVo.setObjectId(objectId);

                   fileDirVo.setObjectType(objectType);

                   fileDirVo.setParentId(fileNodeDetailVo.getDirId());

                  

//               rootEUIFileDir.setParentId(rootEUIFileDir.getDirId());

                 

                   List<FileNodeDetailVo> childFileNodeDetailVoList = new ArrayList<FileNodeDetailVo>();

                   fileDirVo.setDirName(srchText);

                   List<FileDirVo> fileDirVoList = fileDirService.getListByParentIdAndObject(fileDirVo);

                  

                   List<FileNodeDetailVo> dirFileNodeDetailVoList = changeListClass(fileDirVoList, FileNodeDetailVo.class);

                  

                   childFileNodeDetailVoList.addAll(dirFileNodeDetailVoList);

                  

                   List<FileNodeDetailVo> fileFileNodeDetailVoList = getFileNodeListByDirId(dirId,srchText);

                  

                   childFileNodeDetailVoList.addAll(fileFileNodeDetailVoList);

                  

                   fileNodeDetailVo.setChildNode(childFileNodeDetailVoList);

                  

                   return fileNodeDetailVo;

         }

 

         @Override

         public FileNodeDetailVo getFileSystemNodeByDirId(String objectType, String objectId, Long dirId) {

                   // TODO Auto-generated method stub

                   return getFileSystemNodeByDirId(objectType,objectId,dirId, null, null);

         }

 

         @Override

         public void recycleFileDirLink(RecycleVo recycle) {

                   FileDirLinkVo fileDirLinkVo =this.getById(recycle.getObjectId());

                   if(fileDirLinkVo!=null){

                            fileDirLinkVo.setIsDelete(false);

                            this.updateFileDirLink(fileDirLinkVo);

                           

                            recycle.setIsDelete(1L);

                            recycleService.updateRecycle(recycle);

                   }

         }

 

         @Override

         public void updateFileDirLinkVoAndSaveRecycle(FileNodeVo rFileNodeVo) {

                   if(rFileNodeVo.getFId()!=null){

                            FileDirLinkVo fileDirLinkVo =this.getByfId(rFileNodeVo.getFId());

                            if(fileDirLinkVo.getIsDelete()==true)

                                     return;

                            if(fileDirLinkVo != null){

                                     this.delByFDirId(fileDirLinkVo);

                                    

                                     FileDirVo fileDirVo = fileDirService.getById(fileDirLinkVo.getDirId());

                                     RecycleVo recycleVo = new RecycleVo();

                                     recycleVo.setObjectId(fileDirLinkVo.getFDirId());

                                     recycleVo.setParentId(fileDirVo!=null?fileDirVo.getParentId():0L);

                                     recycleVo.setIsDelete(0L);

                                     recycleVo.setObjectType(0L);

                                     recycleVo.setLastUpdateDate(rFileNodeVo.getLastUpdateDate());

                                     recycleVo.setLastUpdatedBy(userService.getSessionUserInfoVo().getId());

                                     recycleService.createRecycle(recycleVo);

                            }

                   }

         }

 

         private FileDirVo rootFile(String objectType,String objectId,Long dirId,FileDirVo rootFileDirVo ){

                   rootFileDirVo = null;

                   if(dirId == 0){

                            rootFileDirVo =         fileDirService.getRootDirByObject(objectType,objectId);

                            dirId = rootFileDirVo.getDirId();

                   }else{

                            rootFileDirVo =fileDirService.getById(dirId);

                   }

                  

                   if(rootFileDirVo == null){

                            return null;

                   }

                  

 

                   return rootFileDirVo;

         }

        

        

        

}
