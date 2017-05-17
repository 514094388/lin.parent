package lin.core.util;

 

import java.util.HashMap;

import java.util.Map;

 

import org.springframework.cglib.beans.BeanCopier;

 

public class CachedBeanCopier {

        

        

         static final Map<String,BeanCopier> BEAN_COPIERS = new HashMap<String,BeanCopier>();

        

        

        

         public static void copy(Object srcObj,Object destObj){

                   String key  = getKey(srcObj.getClass(), destObj.getClass());

                   BeanCopier copier = null;

                  

                   if(BEAN_COPIERS.containsKey(key)){

                            copier = BEAN_COPIERS.get(key);

                   }else{

                            copier = BeanCopier.create(srcObj.getClass(), destObj.getClass(), false);

                            BEAN_COPIERS.put(key, copier);

                   }

                   copier.copy(srcObj, destObj, null);

         }

        

         public static <T>  T copyClass(Object srcObj,Class<T> clazz){

                   String key  = getKey(srcObj.getClass(), clazz);

                  

                   try {

                            T newClazz = clazz.newInstance();

                           

                            BeanCopier copier = null;

                            if(BEAN_COPIERS.containsKey(key)){

                                     copier = BEAN_COPIERS.get(key);

                            }else{

                                     copier = BeanCopier.create(srcObj.getClass(), clazz, false);

                                     BEAN_COPIERS.put(key, copier);

                            }

                           

                            copier.copy(srcObj, newClazz, null);

                           

                            return newClazz;

                   } catch (InstantiationException e) {

                            // TODO Auto-generated catch block

                            e.printStackTrace();

                   } catch (IllegalAccessException e) {

                            // TODO Auto-generated catch block

                            e.printStackTrace();

                   }

                   return null;

         }

        

         private static String getKey(Class<?> srcClazz,Class<?> destClazz) {

                   return srcClazz.getName()+destClazz.getName();

         }

        

 

}
