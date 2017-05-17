package lin.file.service.impl;

 

import java.util.List;

 

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

 

import lin.core.common.AbstractServiceBase;

import lin.core.user.service.IUserService;

 

import lin.file.dao.IRecycleDao;

import lin.file.service.IRecycleService;

import lin.file.vo.RecycleVo;

 

@Service("recycleService")

public class RecycleService extends AbstractServiceBase implements IRecycleService {

 

         @Autowired

         private IRecycleDao        iRecycleDao;

        

         @Autowired

         private IUserService userService;

 

         public long createRecycle(RecycleVo recycleVo) {

                  

                   recycleVo.setCreatedBy(userService.getSessionUserInfoVo().getId());

                   return iRecycleDao.createRecycle(recycleVo);

         }

 

         public List<RecycleVo> getFileListByObject(RecycleVo recycleVo) {

                   return iRecycleDao.getFileListByObject(recycleVo);

         }

 

         @Override

         public RecycleVo getRecycleByRecycleId(Long recycleId) {

                   return iRecycleDao.getRecycleByRecycleId(recycleId);

         }

 

         @Override

         public Long delByRecycleId(RecycleVo recycleVo) {

                   recycleVo.setLastUpdatedBy(userService.getSessionUserInfoVo().getId());

                   return iRecycleDao.delByRecycleId(recycleVo);

         }

 

         @Override

         public Long updateRecoverByRecycleId(RecycleVo recycleVo) {

                   recycleVo.setLastUpdatedBy(userService.getSessionUserInfoVo().getId());

                   return iRecycleDao.updateRecoverByRecycleId(recycleVo);

         }

        

         @Override

         public Long updateRecycle(RecycleVo recycleVo) {

                   recycleVo.setLastUpdatedBy(userService.getSessionUserInfoVo().getId());

                   return iRecycleDao.updateRecycle(recycleVo);

         }

        

        

        

        

        

        

}

 

 

 
