package lin.test;

 

import java.util.concurrent.Callable;

 

import org.apache.commons.httpclient.HttpClient;

import org.apache.commons.httpclient.methods.PostMethod;

 

public class HttpPostJsonCallable implements Callable<String>{

        

         HttpClient httpClient = new HttpClient();

        

         int index;

        

         String cookie = "hwssotinter=24-73-B1-88-E3-A2-7B-02-E8-CD-2A-99-93-1A-20-11; uid=\"\"; hwsso_uniportal=\"\"; logFlag=out; lang=zh; hwssot=C6-A6-5D-98-FB-F1-06-C9-F2-3F-D7-D4-93-4B-61-A7; hwsso_login=Urza0YAIYpw_bJe3YaS9DYVETNuwWOpluDnxCypnmZPaC1afdKlDOwW2KoG_bnQk2pFI2WvFaLIX8q95U8JNDDgTVx8YE8RiPsr9BMKO18KMlZOZxOPSiWhGCtW4s6G4mZ9Hr3XIJt0G4RNwNOs0AN7JpczSXWx7T1FpJxXYaOOozCpG9gP8SabpekOQ_atQftV_aW5kJtI8xVBXhRzcncvyk1xiK9lxJh_a3zFWmxAEzpcevribysOCQJlzljMWKqw4Lw12PmStHvM2Oep4O9im6jqrTVlvQUhjpZrSSjD8Yjtyo5y0ry9Sw_b1ZtbBttVmsq79WwVMSpVbN1Wp3difZVag_c_c; login_logFlag=in; suid=5A-00-E8-D5-12-6E-E2-0E-F8-2E-80-0E-92-58-96-48; w3Token=5A-00-E8-D5-12-6E-E2-0E-DB-6B-95-3B-59-3C-0A-BE-F0-95-4C-B8-0D-C2-6A-90-0D-52-72-09-58-BB-CC-4B; login_uid=5A-00-E8-D5-12-6E-E2-0E-F8-2E-80-0E-92-58-96-48; _sid_=15-CF-C8-1F-CD-60-40-C1-A5-88-D4-57-90-96-92-BD-74-8B-76-0B-74-FB-DC-70-A0-21-48-76-06-B5-F5-76-7B-F6-AE-F1-C0-28-1C-22; hwsso_am=77-22-F7-0E-84-9F-31-CD; login_sip=57-FD-EF-47-61-35-99-4B-E3-6E-5B-4D-FD-1D-8B-35; login_sid=15-CF-C8-1F-CD-60-40-C1-A5-88-D4-57-90-96-92-BD-74-8B-76-0B-74-FB-DC-70-A0-21-48-76-06-B5-F5-76-7B-F6-AE-F1-C0-28-1C-22; LtpaToken=cZOL//MWulNmHmXWDocm2OwiybK3ViMGZW532f1+PN1072QmkmzEHTnwaEQk85aYLdSc2ZnhBbrGzqC+HR4tygKFvKb0ayufXzTLQ4QeQ+47e/J6iX87jmOIuKEDY5ix2VoPbzKPZLjBYbkMtidqtGaTDrdFW3ouMbBiWct18DkMH2ao60rZ/y2Nh4lcxUvtAvb+KleRPmw/FG1NLuQ+WKOJXSTZL+torDowjsqTXlaLddIzoIVnzLKdb6rRroahY4J0qWppVhQTIM2l5h0mfVtXzRdrLR0RDAlSgOijonM4b54TIMOaCkVxlrcNG6SHsV6TyH4EN5JbRR0SQaFuBaBt0cLMKcW3VlgCus5WQztE97pmyg3vDQ==; _dmpa_id=815093142a1bd68484c84338271211492172450605.1492172448.5.1494382247.1493397837.; _dmpa_ses=e018a6bd2a73c5447ebae22429f79dab8c6affb8; JSESSIONID=CA5EAF626FCD13B91D0F93CE83C0EA8D; hwssotinter3=25576016655457; hwssot3=25576016655457; hwa_track_id=6160a29c45a6753e2465c97581ddf9df0a2191e1";

        

         String authorization = "Authorization:Basic aHVhd2VpLlBULmV1aTpvcXJ5NnNmR01UQm01b0dYanJMV1FIOHMzYkphdE9rS0lpYWc1WkF4R2FFSVllM1MwZDJZalBHNlJJZVcvMVlzNEttNng5SE9sUXlVM3FQUHhBRzF2clZPT3ZhOThkK1VPYVkvYlp1UXp5cTlSNGdPSmdnT2g4SDYzYUY2bUtqa0pWQ3NHSzZjZWUxZWtHZm9DZldzOFlzQTk2akhhZDhCS1hXVzA5dXU3SlhCdFllemdzd3RmZz09bz0wMDAwMzAwdD0x";

        

         String url = "http://app.huawei.com/sdcp/publicservices/soa/rest/iwork/getStatusNumByRegion";

        

         String in = "{\"projectCode\": \"563149A\", \"activityId\": \"6290000\", \"time\": \"year\", \"startDate\": \"2017/1/1\",\"endDate\":\"2017/12/31\"}";

 

         public HttpPostJsonCallable(int index) {

                   super();

                   this.index = index;

         }

 

         public void setInParam(String inParam){

                   this.in = inParam;

         }

        

        

         public void setUrl(String url){

                   this.url = url;

         }

        

         public void setAuthorization(String authorization){

                   this.authorization = authorization;

         }

 

 

         @Override

         public String call() throws Exception {

                   // TODO Auto-generated method stub

//               CallResult callResult = new CallResult();

                   PostMethod post = null;

                   String response = null;

                  

                   post = new PostMethod(url);

                   post.addRequestHeader("Accept", "application/json");

                   post.addRequestHeader("Cookie", this.cookie);

                   if(this.authorization != null){

                            post.addRequestHeader("Authorization", this.authorization);

                   }

                   post.setRequestBody(in);

//               callResult.setStartTime(System.currentTimeMillis());

                   int result = this.httpClient.executeMethod(post);

//               callResult.setEndTime(System.currentTimeMillis());

//               callResult.setTimes(((double)callResult.getEndTime()-(double)callResult.getStartTime())/1000);

                   response = post.getResponseBodyAsString().trim();

//               if (result == 200) {

//                         Header[] headers = post.getResponseHeaders();

//                         for(Header header:headers){

//                                  System.out.println(header.getName() + ":" + header.getValue());

//                         }

//                         System.out.println(response);

//               }else{

//                         System.out.println(response);

//               }

                   return response;

         }

        

         public static void main(String[] args) throws Exception{

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

                   HttpPostJsonCallable httpPostJsonCallable = new HttpPostJsonCallable(1);

                   httpPostJsonCallable.setUrl(url);

                   httpPostJsonCallable.setInParam(inParam);

                   httpPostJsonCallable.call();

         }

        

         public void println(){

//               Lock(E2eServiceTest.class);

         }

 

}
