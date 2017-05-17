package lin.file.service;

 

import java.util.List;

 

import lin.file.vo.FileVo;

import lin.file.vo.RecycleVo;

 

 

 

 

public interface IFileService{

        

         public Long createFile(FileVo fileVo);

        

         public Long updateFileByFileId(FileVo fileVo);

        

         public FileVo getFileByFid(Long fId);

        

         public FileVo getFileByFileId(Long fileId);

        

         public List<FileVo> getFileListByFid(Long fId);

        

         public Long delByFId(FileVo fileVo);

 

         public Long delByFileId(FileVo fileVo);

 

         public Long copyFile(Long fId);

 

         /**

         * 设置File的是否删除标识是否，并更新Recycle的删除标识为是

         * @param recycle

         */

         public void recycleFile(RecycleVo recycle);

         /**

         * 根据fileId更新File表是否删除标识，并保存进Recycle表

         * @param fileDeleteList

         * @return 成功删除后的fileId

         */

         public List<String> updateFileAndSaveRecycleByFileId(List<String> fileDeleteList);

        

        

}
