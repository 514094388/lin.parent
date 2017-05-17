package lin.file.dao;

 

import java.util.List;

 

import org.apache.ibatis.annotations.Param;

import org.springframework.stereotype.Component;

 

import lin.file.vo.FileVo;

 

 

 

 

@Component

public interface IFileDao {

 

         public long createFile(@Param("vo")FileVo fileVo);

        

         public Long updateFileByFileId(@Param("vo") FileVo fileVo);

        

         public FileVo getFileByFid(Long fId);

        

         public List<FileVo> getFileListByFid(Long fId);

        

         public FileVo getFileByFileId(Long fileId);

        

         public Long delByFId(@Param("vo") FileVo fileVo);

        

         public Long delByFileId(@Param("vo") FileVo fileVo);

 

         public long copyFile(@Param("fId")Long fId, @Param("newFId")Long newFId, @Param("uid")Long uid);

        

}
