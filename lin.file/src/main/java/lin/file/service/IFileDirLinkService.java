package lin.file.service;

 

import java.util.List;

 

import lin.file.vo.FileDirLinkVo;

import lin.file.vo.FileDirVo;

import lin.file.vo.FileNodeDetailVo;

import lin.file.vo.FileNodeVo;

import lin.file.vo.RecycleVo;

 

 

 

public interface IFileDirLinkService{

        

         public Long addFileDirLink(FileDirLinkVo fileDirLinkVo);

        

         public Long updateFileDirLink(FileDirLinkVo fileDirLinkVo);

        

         public FileDirLinkVo getById(Long dirId);

        

         public FileDirLinkVo getByfId(Long fId);

 

         public List<FileDirLinkVo> getListByDirId(Long dirId);

        

//      public List<EUIFile> getFileListByDirId(Long dirId);

        

         public List<FileNodeDetailVo> getFileNodeListByDirId(Long dirId);

 

         public Long delByFDirId(FileDirLinkVo fileDirLinkVo);

        

//      public FileNode getFileSystemNodeByDirId(String objectType,String objectId,Long dirId);

        

         public FileNodeDetailVo getFileSystemNodeWithHistoryByDirId(String objectType,String objectId,Long dirId,FileDirVo rootFileDirVo);

        

         public FileNodeDetailVo getFileSystemNodeByDirId(String objectType,String objectId,Long dirId);

 

         public FileNodeDetailVo getFileSystemNodeByDirId(String objectType,String objectId,Long dirId, String srchText,FileDirVo rootFileDirVo);

        

         /**

         * 更新FileDirLink中的是否删除标识，并更新回收站记录。

         */

         public void recycleFileDirLink(RecycleVo recycle);

        

         /**

         * 更新FileDirLink中的是否删除标识并保存回收记录

         * @param fId

         */

         public void updateFileDirLinkVoAndSaveRecycle(FileNodeVo rFileNodeVo);

        

}
