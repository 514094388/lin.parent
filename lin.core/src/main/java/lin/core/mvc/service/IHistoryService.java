package lin.core.mvc.service;

 

import java.util.List;

 

import lin.core.common.PageVo;

import lin.core.mvc.vo.HistoryVo;

import lin.core.mvc.vo.QHistoryVo;

import lin.core.mvc.vo.RHistoryVo;

 

 

public interface IHistoryService{

        

//      public Long addEuiHistory(EUIHistory euiHistory);

        

 

         public Long addHistory(HistoryVo historyVo, Object previous, Object after);

        

         public PageVo<List<RHistoryVo>> getHistoryListByPagination(QHistoryVo  qHistoryVo);

        

}
