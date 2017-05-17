package lin.file.dao;

 

import java.util.List;

 

import org.apache.ibatis.annotations.Param;

import org.springframework.stereotype.Component;

 

import lin.file.vo.RecycleVo;

 

 

 

 

@Component

public interface IRecycleDao {

 

         public long createRecycle(RecycleVo recycleVo);

        

         public List<RecycleVo> getFileListByObject(RecycleVo recycleVo);

        

         public RecycleVo getRecycleByRecycleId(Long recycleId);

        

         public Long delByRecycleId(RecycleVo recycleVo);

        

         public Long updateRecoverByRecycleId(RecycleVo recycleVo);

        

         public Long updateRecycle(@Param("vo") RecycleVo recycleVo);

        

}
