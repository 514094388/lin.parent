package lin.test;

 

import java.util.HashSet;

import java.util.Set;

import java.util.concurrent.ExecutionException;

import java.util.concurrent.ExecutorService;

import java.util.concurrent.Executors;

import java.util.concurrent.Future;

 

public abstract class CallBaseAbstract {

        

         public static void call(String url,String inParam) throws InterruptedException, ExecutionException{

                   call(url,inParam,null,50);

         }

        

         public static void call(String url,String inParam,int threadSize) throws InterruptedException, ExecutionException{

                   call(url,inParam,null,threadSize);

         }

        

         public static void call(String url,String inParam,String authorization) throws InterruptedException, ExecutionException{

                   call(url,inParam,authorization,50);

         }

 

         public static void call(String url,String inParam,String authorization,int threadSize) throws InterruptedException, ExecutionException{

                  

                   ExecutorService pool = Executors.newFixedThreadPool(threadSize);

                   Set<Future<String>> futureSet = new HashSet<Future<String>>();

                   for(int i=0;i<threadSize;i++){

                            HttpPostJsonCallable httpPostJsonCallable = new HttpPostJsonCallable(i);

                            httpPostJsonCallable.setInParam(inParam);

                            httpPostJsonCallable.setAuthorization(authorization);

                            Future<String> httpPostJsonFuture = pool.submit(httpPostJsonCallable);

                            futureSet.add(httpPostJsonFuture);

                   }

                   int i = 1;

                   String oldResponce = null;

                   for(Future<String> httpPostJsonFuture:futureSet){

                            String newResponce = httpPostJsonFuture.get();

                            System.out.println("线程" + i + ":" + newResponce);

                            if(!newResponce.equals(oldResponce)){

                                     System.out.println("线程" + i + ":" + "值发生变化");

                            }

                            oldResponce = newResponce;

                            i++;

                   }

                   pool.shutdown();

         }

}
