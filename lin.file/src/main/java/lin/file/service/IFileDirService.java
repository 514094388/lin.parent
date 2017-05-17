package lin.file.service;

 

import java.util.Date;

import java.util.List;

 

import lin.file.vo.FileDirVo;

import lin.file.vo.FileNodeDetailVo;

import lin.file.vo.RecycleVo;

 

 

public interface IFileDirService{

        

         public Long addFileDir(FileDirVo fileDirVo);

        

         public Long updateFileDir(FileDirVo fileDirVo);

        

         public FileDirVo getById(Long dirId);

 

         public Long delByDirId(FileDirVo fileDirVo);

        

         public List<FileDirVo> getListByParentIdAndObject(FileDirVo fileDirVo);

        

         public FileDirVo getRootDirByObject(String objectType,String objectId);

 

         public void addFIdToDir(Long fId, Long dirId,String objectType,String objectId);

 

         public void updateFileSizeByObjectAndDirId(String objectType,String objectId, Long dirId);

 

         public FileNodeDetailVo delDirAndFileByObjectAndDirId(String objectType, String objectId, Long dirId);

        

         public FileNodeDetailVo getAllInfoByDirId(String objectType, String objectId, Long dirId);

 

         public void copyFileDir(FileDirVo fileDirVo, Long toDirId);

        

         public List<FileDirVo> getListByParentId(Long parentId);

        

         /**

         * 删除文件夹

         * 更新FileDir表的parentId字段

         * 保存Recycle表

         * @param fileDirVo

         * @param updateDate

         */

         public void updateDirAndSaveDirInfo(FileDirVo fileDirVo,Date updateDate);

        

         /**

         * 根据recycle，更新parentId到FileDirVo中的parentId，并更新recycle表

         * @param recycle

         */

         public void recycleFileDir(RecycleVo recycle);

 

}
