package lin.file.service.impl;

 

import java.io.File;

import java.io.FileInputStream;

import java.io.InputStream;

import java.io.OutputStream;

import java.io.PrintWriter;

import java.net.URLDecoder;

import java.util.HashMap;

import java.util.Map;

 

import javax.servlet.http.HttpServletResponse;

 

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

 

import com.alibaba.fastjson.JSONObject;

import lin.core.common.AbstractServiceBase;

 

import lin.file.service.IFileService;

import lin.file.service.IWopiService;

import lin.file.vo.FileVo;

 

@Service("wopiService")

public class WopiService extends AbstractServiceBase implements IWopiService {

 

         private static Logger logger = Logger.getLogger(WopiService.class);

        

         @Autowired

         private IFileService fileService;

 

         @Value("${uploadPath}")

         private String uploadPath;

        

         @Value("${wopiClient}")

         private String wopiClient;

 

         public static String DOCX_FILE = ".docx";

         public static String PPTX_FILE = ".pptx";

         public static String PDF_FILE = ".pdf";

        

         public void checkFileInfo(HttpServletResponse res, Long fileId){

                   try {

                            FileVo fileVo = fileService.getFileByFileId(fileId);

                            String fileName = fileVo.getFilePath();

                            String version = String.valueOf(fileVo.getVersion());

                            JSONObject obj = new JSONObject();

                            String filePath = uploadPath + fileName;

                            File f = new File(filePath);

                            obj.put("Size", f.length());

                            obj.put("Version", version);

                            obj.put("BaseFileName", URLDecoder.decode(fileName, "UTF-8"));

                            obj.put("OwnerId", "1.0");

                            obj.put("ProtectInClient", true);

                            logger.info("wopiserviceview result = " + obj.toString());

                            res.setCharacterEncoding("UTF-8");

                            res.setContentType("application/json;charset=UTF-8");

                            PrintWriter out = null;

                            try {

                                     out = res.getWriter();

                                     out.write(obj.toString());

                                     res.setStatus(200);

                            } finally {

                                     if (out != null) {

                                               out.close();

                                     }

                            }

                   } catch (Exception e) {

                            res.setStatus(500);

                   }

         }

 

         @Override

         public void getFile(HttpServletResponse res, Long fileId) {

                   // TODO Auto-generated method stub

                   InputStream is = null;

                   OutputStream os = null;

                   File f = null;

                   try {

                            FileVo fileVo = fileService.getFileByFileId(fileId);

                            String fileName = fileVo.getFilePath();

                            logger.info("getWithContent fileName ="+fileName);

                            long fileSize = 0;

                            String filePath = uploadPath + fileName;

                            logger.info("getWithContent filePath ="+filePath);

                            f = new File(filePath);

                            fileSize = f.length();

                            is = new FileInputStream(f);

                            try {

                                     res.reset();

                                     // 设置response的Header

                                     res.addHeader("Content-Disposition", "attachment;filename="

                                                        + fileName.substring(fileName.lastIndexOf("/") + 1));

                                     res.addHeader("Content-Length", "" + fileSize);

                                     os = res.getOutputStream();

                                     res.setContentType("application/octet-stream");

                                     byte[] b = new byte[1024];

                                     int len;

                                     len = is.read(b);

                                     while (len > 0) {

                                               os.write(b, 0, len);

                                               len = is.read(b);

                                     }

                                     os.flush();

                                     res.setStatus(200);

                            } finally {

                                     if (is != null) {

                                               is.close();

                                     }

                                     if (os != null) {

                                               os.close();

                                     }

                            }

                   } catch (Exception e) {

                            res.setStatus(500);

                   }

         }

 

         @Override

         public Map<String, String> viewFile(Long fileId) {

                   Map<String, String> resultMap = new HashMap<String, String>();

                   try {

                            FileVo fileVo = fileService.getFileByFileId(fileId);

                            String fileType = fileVo.getFileType();

                            String url = null;

                            if(DOCX_FILE.equalsIgnoreCase(fileType)){

                                     String docxUrlSrc = "http://" + wopiClient + "/wv/wordviewerframe.aspx?WOPISrc=";

                                     url = docxUrlSrc;

                            }else if(PPTX_FILE.equalsIgnoreCase(fileType)){

                                     String docxUrlSrc = "http://" + wopiClient + "/wv/wordviewerframe.aspx?PdfMode=1&WOPISrc=";

                                     url = docxUrlSrc;

                            }else if(PDF_FILE.equalsIgnoreCase(fileType)){

                                     String docxUrlSrc = "http://" + wopiClient + "/p/PowerPointFrame.aspx?PowerPointView=ReadingView&WOPISrc=";

                                     url = docxUrlSrc;

                            }

                            resultMap.put("success", "true");

                            resultMap.put("url", url);

                   } catch (Exception e) {

                            // TODO: handle exception

                            logger.error(e.getMessage());

                            resultMap.put("success", "false");

                   }

                   return resultMap;

         }

 

}
