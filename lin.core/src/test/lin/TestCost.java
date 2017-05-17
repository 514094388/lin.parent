package lin.core;

 

import java.util.HashSet;

import java.util.Set;

 

public class TestCost {

 

         public static void main(String[] args) {

                   // TODO Auto-generated method stub

                   long start = 0;

                   long end = 0;

                   Set<Integer> columns = new HashSet<Integer>();

                   String s = null;

                   start = System.currentTimeMillis();

                   for(int i=0;i<100000;i++){

                            columns.add(i);

                   }

                   for(int i=0;i<100000;i++){

                            columns.contains(i);

                            if(s != null){

                                    

                            }

                   }

                   end = System.currentTimeMillis();

                   System.out.println("Cost=" + (end-start));

         }

 

}
