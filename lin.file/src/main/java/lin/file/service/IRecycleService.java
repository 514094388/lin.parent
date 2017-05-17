package lin.file.service;

 

import java.util.List;

 

import lin.file.vo.RecycleVo;

 

 

public interface IRecycleService{

        

         public long createRecycle(RecycleVo recycleVo);

        

         public List<RecycleVo> getFileListByObject(RecycleVo recycleVo);

        

         public RecycleVo getRecycleByRecycleId(Long recycleId);

        

         public Long delByRecycleId(RecycleVo recycleVo);

        

         public Long updateRecoverByRecycleId(RecycleVo recycleVo);

        

         public Long updateRecycle(RecycleVo recycleVo);

        

}
