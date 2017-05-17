package lin.core.util;

 

import java.math.BigDecimal;

import java.util.Date;

 

public class DateUtil<T> {

        

         public static double minus(Date subtrahend,Date minuend){

                   if(subtrahend == null || minuend == null){

                            return 0;

                   }

                   return ((double)(subtrahend.getTime() - minuend.getTime())) / (1000 * 3600 * 24);

         }

         public static double minus(Date subtrahend,Date minuend,int dotNum){

                   if(subtrahend == null || minuend == null){

                            return 0;

                   }

                   double result = ((double)(subtrahend.getTime() - minuend.getTime())) / (1000 * 3600 * 24);

                   BigDecimal   b   =   new   BigDecimal(result); 

                   return b.setScale(dotNum,   BigDecimal.ROUND_HALF_UP).doubleValue();

         }

        

         public static int minusInt(Date subtrahend,Date minuend){

                   if(subtrahend == null || minuend == null){

                            return 0;

                   }

                   double result = ((double)(subtrahend.getTime() - minuend.getTime())) / (1000 * 3600 * 24);

                   BigDecimal   b   =   new   BigDecimal(result); 

                   return b.setScale(0,   BigDecimal.ROUND_HALF_UP).intValue();

         }

 

}
