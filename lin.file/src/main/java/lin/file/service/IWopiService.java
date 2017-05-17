package lin.file.service;

 

import java.util.Map;

 

import javax.servlet.http.HttpServletResponse;

 

public interface IWopiService {

         public void checkFileInfo(HttpServletResponse res, Long fileId);

        

         public void getFile(HttpServletResponse res, Long fileId);

        

         public Map<String, String> viewFile(Long fileId);

}
