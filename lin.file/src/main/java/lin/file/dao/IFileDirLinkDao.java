package lin.file.dao;

 

import java.util.List;

 

import org.apache.ibatis.annotations.Param;

import org.springframework.stereotype.Component;

 

import lin.file.vo.FileDirLinkVo;

import lin.file.vo.FileNodeDetailVo;

 

 

 

 

@Component

public interface IFileDirLinkDao {

 

 

         public Long addFileDirLink(FileDirLinkVo fileDirLinkVo);

        

         public Long updateFileDirLink(@Param("vo") FileDirLinkVo fileDirLinkVo);

        

         public FileDirLinkVo getById(Long dirId);

        

         public FileDirLinkVo getByfId(Long fId);

        

         public List<FileDirLinkVo> getByDirId(Long dirId);

        

         public List<FileDirLinkVo> getListByDirId(Long dirId);

        

         public Long delByFDirId(@Param("vo") FileDirLinkVo fileDirLinkVo);

        

         public List<FileNodeDetailVo> getFileNodeListByDirId(@Param("dirId")Long dirId,@Param("srchText")String srchText);

 

        

}
