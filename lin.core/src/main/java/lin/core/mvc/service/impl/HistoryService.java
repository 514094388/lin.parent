package lin.core.mvc.service.impl;

 

import java.util.Collection;

import java.util.List;

 

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

 

import com.alibaba.fastjson.JSONObject;

import lin.core.common.AbstractServiceBase;

import lin.core.common.PageVo;

import lin.core.mvc.dao.IHistoryDao;

import lin.core.mvc.service.IHistoryService;

import lin.core.mvc.vo.HistoryVo;

import lin.core.mvc.vo.QHistoryVo;

import lin.core.mvc.vo.RHistoryVo;

import lin.core.user.service.IUserService;

import lin.core.user.vo.UserInfoVo;

import de.danielbechler.diff.ObjectDifferFactory;

import de.danielbechler.diff.node.Node;

 

@Service("historyService")

public class HistoryService extends AbstractServiceBase implements IHistoryService {

 

         @Autowired

         IHistoryDao    iHistoryDao;

        

         @Autowired

         IUserService userService;

        

        

         public Long addHistory(HistoryVo historyVo,final Object previous,final Object after){

        

                   Node root = ObjectDifferFactory.getInstance().compare(after, previous);

                   JSONObject baseJson = new JSONObject();

                   JSONObject afterJson = new JSONObject();

                  

                   Collection<Node> childs = root.getChildren();

                   for (Node node : childs) {

                           

                            Object baseValue = node.canonicalGet(previous);

                            Object afterValue = node.canonicalGet(after);

                           

                            baseJson.put(node.getPathElement().toString(), baseValue);

                            afterJson.put(node.getPathElement().toString(), afterValue);

                           

                   }

                  

                   historyVo.setPrevious(baseJson.toJSONString());

                   historyVo.setAfter(afterJson.toJSONString());;

        

                   return iHistoryDao.addHistory(historyVo);

         }

        

        

        

         public PageVo<List<RHistoryVo>> getHistoryListByPagination(QHistoryVo  qHistoryVo){

                  

                   PageVo<List<RHistoryVo>> rHistoryVoListPageVo = new PageVo<List<RHistoryVo>>(qHistoryVo);

                  

                            List<HistoryVo> historyVoList = iHistoryDao.getHistoryListByPagination(qHistoryVo,rHistoryVoListPageVo);

                           

                           

                            List<RHistoryVo> rHistoryVoList = changeListClass(historyVoList, RHistoryVo.class);

                           

                            for (RHistoryVo rHistoryVo : rHistoryVoList) {

                                    

                                     Long createdBy = rHistoryVo.getCreatedBy();

                                     if(createdBy != null){

                                               UserInfoVo createdByUserInfoVo = new UserInfoVo();

                                               createdByUserInfoVo.setId(createdBy);

                                               createdByUserInfoVo = userService.getUserInfoVo(createdByUserInfoVo);

                                               rHistoryVo.setCreatedByUser(createdByUserInfoVo);

                                     }

                                    

                                     Long lastUpdatedBy = rHistoryVo.getLastUpdatedBy();

                                     if(lastUpdatedBy != null){

                                              

                                               UserInfoVo lastUpdatedByUserInfoVo = new UserInfoVo();

                                               lastUpdatedByUserInfoVo.setId(lastUpdatedBy);

                                               lastUpdatedByUserInfoVo = userService.getUserInfoVo(lastUpdatedByUserInfoVo);

                                               rHistoryVo.setLastUpdatedByUser(lastUpdatedByUserInfoVo);

                                      }

                            }

                           

                           

                            Long historyListCount = iHistoryDao.getHistoryListByPaginationCount(qHistoryVo);

                           

                            rHistoryVoListPageVo.setData(rHistoryVoList);

                            rHistoryVoListPageVo.setTotal(historyListCount);

                  

                   return rHistoryVoListPageVo;

         }

        

        

}
