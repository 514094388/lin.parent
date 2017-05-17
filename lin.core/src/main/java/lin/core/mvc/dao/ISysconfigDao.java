package lin.core.mvc.dao;

 

import java.util.List;

 

import org.apache.ibatis.annotations.Param;

 

import lin.core.mvc.vo.RTaskCategoryDefaultVo;

 

public interface ISysconfigDao {

        

         public List<RTaskCategoryDefaultVo> getTaskCategoryDefault(@Param("configType")String configType,@Param("columns")String columns);

 

}
