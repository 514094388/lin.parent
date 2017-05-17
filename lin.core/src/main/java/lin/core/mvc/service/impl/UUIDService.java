package lin.core.mvc.service.impl;

 

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

 

import lin.core.mvc.dao.IDBDao;

import lin.core.mvc.service.IUUIDService;

 

@Service("uUIDService")

public class UUIDService implements IUUIDService {

 

        

         @Autowired

         private IDBDao idBDao;

        

         public long generateKey(){

                   return idBDao.generateKey();

         }

        

}
