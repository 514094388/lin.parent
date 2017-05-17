package lin.core.mvc.service.impl;

 

import java.util.List;

 

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

 

import lin.core.mvc.dao.ISysconfigDao;

import lin.core.mvc.service.ISysconfigService;

import lin.core.mvc.vo.RTaskCategoryDefaultVo;

@Service("sysconfigService")

public class SysconfigService implements ISysconfigService {

        

         @Autowired

         ISysconfigDao sysconfigDao;

 

         @Override

         public List<RTaskCategoryDefaultVo> getTaskCategoryDefault() {

                   // TODO Auto-generated method stub

                   return sysconfigDao.getTaskCategoryDefault("eui_task_category", "'category_name' as categoryName, 'sort' as sort");

         }

 

}
