package lin.core.common;

 

import java.util.ArrayList;

import java.util.List;

 

import lin.core.util.CachedBeanCopier;

 

public abstract class AbstractServiceBase{

 

        

        

         public <C,T> List<T> changeListClass(List<C> dataList,Class<T> clazz){

//               try {

                           

                            List<T> list = new ArrayList<T>();

                           

                            for (C object : dataList) {

//                                  T newClazz = clazz.newInstance();

//                                  BeanCopier copier = BeanCopier.create(object.getClass(), clazz, false);

//                                  copier.copy(object,newClazz , null);

//                                  CachedBeanCopier.copy(object, newClazz);

                                    

                                     T newClazz = CachedBeanCopier.copyClass(object, clazz);

                                     if(newClazz != null){

                                               list.add(newClazz);

                                     }

                            }

                           

                            return list;

                           

//               }catch (InstantiationException e) {

//                         e.printStackTrace();

//               } catch (IllegalAccessException e) {

//                         e.printStackTrace();

//               }

//               return null;

         }

        

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

//              

//               return null;

         }

        

        

}
