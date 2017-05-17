package lin.file.dao;

 

import java.util.List;

 

import org.apache.ibatis.annotations.Param;

import org.springframework.stereotype.Component;

 

import lin.file.vo.FileDirVo;

 

 

 

 

@Component

public interface IFileDirDao {

        

         public Long addFileDir(FileDirVo fileDirVo);

        

         public Long updateFileDir(@Param("vo") FileDirVo fileDirVo);

        

         public FileDirVo getById(@Param("dirId")Long dirId);

        

         public Long delByDirId(@Param("vo") FileDirVo fileDirVo);

        

         public List<FileDirVo> getListByParentIdAndObject(@Param("vo")FileDirVo fileDirVo);

        

         public FileDirVo getRootDirByObject(@Param("objectType") String objectType,@Param("objectId") String objectId);

        

         public List<FileDirVo> getListByParentId(@Param("parentId")Long parentId);

        

}
