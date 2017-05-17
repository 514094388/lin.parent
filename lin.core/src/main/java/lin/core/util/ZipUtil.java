package lin.core.util;

 

import java.io.File;

import java.io.FileInputStream;

import java.io.FileOutputStream;

import java.io.InputStream;

import java.util.HashMap;

import java.util.List;

import java.util.Set;

import java.util.zip.ZipEntry;

import java.util.zip.ZipOutputStream;

 

public class ZipUtil {

        

         /**

         * 对一系列文件进行压缩

         * @param hashpath 记录文件,真实文件名和文件夹关系

         * @param zippath 目标压缩文件路径

         */

         public static void zipMultiFiles(HashMap<String,List<String>> hashpath, String zippath) {

                   try {

                            File zipFile = new File(zippath);

                            ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(

                                               zipFile));

                            Set<String> paths=hashpath.keySet();

                            for(String folderpath:paths)

                            {

                                     List<String> filepaths=hashpath.get(folderpath);

                                     for(String filepath:filepaths)

                                     {

                                               File file = new File(filepath);

                                               if (file.exists()) {

                                                        recursionZip(zipOut, file, folderpath);

                                               }

                                     }

                            }                          

                            zipOut.close();

                   } catch (Exception e) {

                            e.printStackTrace();

                   }

         }

        

        

        

        

        

 

         /**

         * 对多个文件进行压缩

         * @param filepaths 文件路径集合

         * @param zippath

         */

         public static void zipMultiFiles(List<String> filepaths, String zippath) {

                   try {

                            File zipFile = new File(zippath);

                            ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(

                                               zipFile));

                            for (String filepath : filepaths) {

                                     File file = new File(filepath);

                                     if (file.exists()) {

                                               recursionZip(zipOut, file, "");

                                     }

                            }

                            zipOut.close();

                   } catch (Exception e) {

                            e.printStackTrace();

                   }

         }

 

         /**

         * 执行压缩

         * @param zipOut

         * @param file

         * @param baseDir

         * @throws Exception

         */

         public static void recursionZip(ZipOutputStream zipOut, File file,

                            String baseDir) throws Exception {

                   if (file.isDirectory()) {

                            File[] files = file.listFiles();

                            for (File fileSec : files) {

                                     recursionZip(zipOut, fileSec, baseDir + file.getName()

                                                        + File.separator);

                            }

                   } else {

                            byte[] buf = new byte[1024];

                            InputStream input = new FileInputStream(file);

                            zipOut.putNextEntry(new ZipEntry(baseDir + file.getName()));

                            int len;

                            while ((len = input.read(buf)) != -1) {

                                     zipOut.write(buf, 0, len);

                            }

                            input.close();

                   }

         }

 

}
