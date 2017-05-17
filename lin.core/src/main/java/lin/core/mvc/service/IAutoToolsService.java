package lin.core.mvc.service;

 

import java.io.IOException;

import java.sql.SQLException;

 

import lin.core.mvc.vo.autotools.QGenerateMVCFromTablesVo;

 

public interface IAutoToolsService {

 

         public void generateMVCFromTabels(QGenerateMVCFromTablesVo qGenerateMVCFromTablesVo) throws Exception;

 

}
