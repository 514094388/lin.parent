package lin.core.common;

 

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;

 

import org.springframework.beans.factory.annotation.Autowired;

 

import com.alibaba.fastjson.JSON;

import lin.core.constants.UserConstants;

import lin.core.user.vo.UserInfoVo;

import lin.core.util.CachedBeanCopier;

 

public abstract class AbstractControllerBase{

        

 

        

         @Autowired

         private  HttpServletRequest request;

        

        

        

         public <T> ResponseVo<T> response(T response){

                   ResponseVo<T> responseVo = new ResponseVo<T>();

                   responseVo.setSuccess(true);

                   responseVo.setCode(200);

                   if(response != null)responseVo.setResponse(response);

                   return  responseVo;

         }

         public <T> ResponseVo<T> response(){

                   return  response(null);

         }

 

//      public <T> ResponseVo<T> handle(Object data,Class<T> clazz){

//               ResponseVo<T> page = new ResponseVo<T>();

//               try {

//                         T newClazz = clazz.newInstance();

//                         BeanCopier copier = BeanCopier.create(data.getClass(), clazz, false);

//                         copier.copy(data,newClazz , null);

//                         page.setData(newClazz);

//                         return  page;

//               } catch (InstantiationException e) {

//                         e.printStackTrace();

//               } catch (IllegalAccessException e) {

//                         e.printStackTrace();

//               }

//               return  error(999, "system has err");

//      }

        

//      public <C,T> List<T> changeListClass(List<C> dataList,Class<T> clazz){

////            try {

//                        

//                         List<T> list = new ArrayList<T>();

//                        

//                         for (C object : dataList) {

////                               T newClazz = clazz.newInstance();

////                               BeanCopier copier = BeanCopier.create(object.getClass(), clazz, false);

////                               copier.copy(object,newClazz , null);

//                                 

//                                  T newClazz = CachedBeanCopier.copyClass(object, clazz);

//                                 

//                                 

//                                  list.add(newClazz);

//                         }

//                        

//                         return list;

//                        

////            }catch (InstantiationException e) {

////                     e.printStackTrace();

////            } catch (IllegalAccessException e) {

////                     e.printStackTrace();

////            }

//              

////            return null;

//      }

//     

         public <T> T changeClass(Object data,Class<T> clazz){

//               try {

                           

//                                  T newClazz = clazz.newInstance();

//                                  BeanCopier copier = BeanCopier.create(data.getClass(), clazz, false);

//                                  copier.copy(data,newClazz , null);

                           

                            T newClazz = CachedBeanCopier.copyClass(data, clazz);

 

                                     return newClazz;

                           

//               }catch (InstantiationException e) {

//                         e.printStackTrace();

//               } catch (IllegalAccessException e) {

//                         e.printStackTrace();

//               }

                  

//               return null;

         }

//     

//     

//     

//     

//      public <T> ResponseVo<List<T>> handleList(List<Object> dataList,Class<T> clazz){

//               ResponseVo<List<T>> page = new ResponseVo<List<T>>();

////            String jsonStr= JSON.toJSONString(data);

////            List<T> obj1 = JSON.parseArray(jsonStr,clazz);

//              

////            try {

//                        

//                         List<T> list = new ArrayList<T>();

//                         for (Object object : dataList) {

////                               T newClazz = clazz.newInstance();

////                               BeanCopier copier = BeanCopier.create(object.getClass(), clazz, false);

////                               copier.copy(object,newClazz , null);

//                                 

//                                  T newClazz = CachedBeanCopier.copyClass(object, clazz);

//                                 

//                                  list.add(newClazz);

//                         }

//                         page.setData(list);

//                         return  page;

////            } catch (InstantiationException e) {

////                     e.printStackTrace();

////            } catch (IllegalAccessException e) {

////                     e.printStackTrace();

////            }

////           

////            return  error(998, "system has err");

//      }

        

        

         /*public <T> PageResult<T> handle(T data){

                   PageResult<T> page = new PageResult<T>();

                   page.setData(data);

                   return  page;

         }*/

   

         public <T> ResponseVo<T> error(long code,String message){

                   ResponseVo<T> responseVo = new ResponseVo<T>();

                   responseVo.setMessage(message);

                   responseVo.setSuccess(false);

                   responseVo.setCode(code);

                   return  responseVo;

         }

        

         public String errorToJson(long code,String message){

                   return  JSON.toJSONString(error(code,message));

         }

        

         /*

         * PageVo表示的是分页 怎么可能有单个

         public  <T,V> PageResult<PageVo<T>> handlePageVo(PageVo<V> obj,Class<T> clazz){

                   Object data = obj.getData();

                   String jsonStr= JSON.toJSONString(data);

                  

                   T obj1 = JSON.parseObject(jsonStr,clazz);

                  

                   PageVo<T> pa = new PageVo<T>();

                   pa.setCurPage(obj.getCurPage());

                   pa.setPageSize(obj.getPageSize());

                   pa.setData(obj1);

                  

                   return handle(pa);

         }

         */

        

//      public  <T,V> ResponseVo<PageVo<List<T>>> handleListPageVo(PageVo<List<V>> obj,Class<T> clazz){

////            try {

//                                           List<V> dataList = obj.getData();

//                                           List<T> list = new ArrayList<T>();

//                                           for (V object : dataList) {

////                                                 T newClazz = clazz.newInstance();

////                                                 BeanCopier copier = BeanCopier.create(object.getClass(), clazz, false);

////                                                 copier.copy(object,newClazz , null);

//                                                     T newClazz = CachedBeanCopier.copyClass(object, clazz);

//                                                     list.add(newClazz);

//                                           }

//                         //               String jsonStr= JSON.toJSONString(date);

//                         //               List<T> list = JSON.parseArray(jsonStr,clazz);

//                                           PageVo<List<T>> pa = new PageVo<List<T>>();

//                                           pa.setPage(obj.getPage());

//                                           pa.setSize(obj.getSize());

//                                           pa.setData(list);

//                                           ResponseVo<PageVo<List<T>>> page = new ResponseVo<PageVo<List<T>>>();

//                                           page.setResponse(pa);

//                                           return  page;

////            } catch (InstantiationException e) {

////                     e.printStackTrace();

////            } catch (IllegalAccessException e) {

////                     e.printStackTrace();

////            }

////            return  error(997, "system has err");

//      }

        

         public UserInfoVo getUserInfoVo(){

                   HttpSession session = request.getSession();

                   return (UserInfoVo)session.getAttribute(UserConstants.USERINFOVO);

         }

        

        

}
