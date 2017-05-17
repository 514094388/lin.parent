package lin.core.mvc.dao;

 

import java.util.Map;

import java.util.Set;

 

import org.apache.ibatis.annotations.MapKey;

import org.apache.ibatis.annotations.Param;

 

import lin.core.mvc.vo.autotools.TableColumnsInfoVo;

import lin.core.mvc.vo.autotools.TableInfoVo;

 

public interface IAutoToolsDao {

 

         @MapKey("tableName")

         public Map<String,TableInfoVo> getTableComments(@Param("tableName")Set<String> tableNameSet);

 

         @MapKey("columnName")

         public Map<String, TableColumnsInfoVo> getColumnsComments(@Param("tableName")String tableName);

 

}
