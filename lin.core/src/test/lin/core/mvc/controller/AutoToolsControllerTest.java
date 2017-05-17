package lin.core.mvc.controller;

 

import lin.test.CallBaseAbstract;

 

public class AutoToolsControllerTest extends CallBaseAbstract {

 

    public static void generateMVCFromTabels() throws Exception{

        int threadSize = 1;

        String url = "http://localhost.huawei.com:8080/api/core/autoTools/generateMVCFromTabels";

        String inParam =

                "[\n" +

                "  {\n" +

                "    \"basePackage\": \"lin.authority\",\n" +

                "    \"qtableLinkVoLists\": [\n" +

                "      {\"table\":\"EUI_TASK t\",\"on\":\"\"},\n" +

                "      {\"innerJoin\":\"TPL_USER_T t1\",\"on\":\"t1.USER_ID = t.CREATED_BY\"}\n" +

                "    ],\n" +

                "    \"savePath\": \"D:\\\\workspace1\\lin.parent\\lin.authority\\src\\main\\java\"\n" +

                "  }\n" +

                "]";

        call(url, inParam, threadSize);

    }

 

   

    public static void main(String[] args) throws Exception{

        generateMVCFromTabels();

    }

}

 
