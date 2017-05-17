package lin.core.mvc.dao;

 

import java.util.List;

 

import org.apache.ibatis.annotations.Param;

import org.springframework.stereotype.Component;

 

import lin.core.common.PageVo;

import lin.core.mvc.vo.HistoryVo;

import lin.core.mvc.vo.QHistoryVo;

 

 

 

@Component

public interface IHistoryDao {

 

         public List<HistoryVo> getHistoryListByPagination(@Param("vo") QHistoryVo qHistoryVo,@Param("page") PageVo<?> pageVo);

        

         public Long getHistoryListByPaginationCount(@Param("vo") QHistoryVo qHistoryVo);

        

        

         public Long addHistory(HistoryVo historyVo);

 

        

}
