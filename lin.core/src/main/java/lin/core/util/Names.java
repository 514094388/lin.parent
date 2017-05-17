package lin.core.util;

 

 

public class Names {

        

         public String tableJaveName;

         public String controllerName;

         public String serviceName;

         public String serviceVarName;

         public String serviceInterfaceName;

         public String daoName;

         public String daoVarName;

         public String daoInterfaceName;

         public String voName;

         public String qVoVarName;

         public String qVoVarNameXMLAlias = "vo";

         public String selectColumnsVarName = "columns";

         public String selectColumnsXMLAlias = "columns";

         public String qVoListVarName;

         public String rVoVarName;

        

         public final String interfaceType = "interface";

         public final String classType = "class";

        

         public final String javaType = ".java";

         public final String xmlType = ".xml";

 

         public final String javaSaveType = "java";

         public final String resourceSaveType = "resources";

        

         public final String saveName = "save";

         public final String deleteName = "delete";

         public final String updateName = "update";

         public final String selectName = "select";

 

         public Names(String tableName) {

                   /*

                   * 生成MVC类名、变量名

                   */

                   tableJaveName = transformDBNameToJavaName(0,tableName);

                   controllerName = tableJaveName + "Controller";

                   serviceName = tableJaveName + "Service";

                   daoName = tableJaveName + "Dao";

                   serviceVarName = firstCharToUpOrToLow(1,serviceName);

                   serviceInterfaceName = "I" + serviceName;

                   serviceVarName = firstCharToUpOrToLow(1,serviceName);

                   serviceInterfaceName = "I" + serviceName;

                   daoInterfaceName = "I" + daoName;

                   daoVarName = firstCharToUpOrToLow(1,daoName);

                   voName = tableJaveName + "Vo";

                   qVoVarName = "q" + voName;

                   rVoVarName = "r" + voName;

                   qVoListVarName = qVoVarName + "List";

         }

        

         public static String firstCharToUpOrToLow(int up0OrLow1,String word){

                   if(up0OrLow1 == 0){

                            return word.substring(0, 1).toUpperCase() + word.substring(1);

                   }else if(up0OrLow1 == 1){

                            return word.substring(0, 1).toLowerCase() + word.substring(1);

                   }

                   return word;

         }

        

         public static String transformDBNameToJavaName(int firstWordUp0OrLow1,String dBName){

                   StringBuffer javaName = new StringBuffer();

                   String[] words = dBName.split("_");

                   for(int i=0;i<words.length;i++){

                            String word = words[i];

                            if(i == 0)

                                     javaName.append(

                                                        (firstWordUp0OrLow1==0?word.substring(0, 1).toUpperCase():word.substring(0, 1).toLowerCase()) +

                                                        word.substring(1).toLowerCase()

                                                        );

                            else

                                      javaName.append(word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase());

                   }

                   return javaName.toString();

         }

        

         public static String basePackageReplaceFromDotToDiagonal(String basePackage){

                   return basePackage.replaceAll("[.]", "/");

         }

        

         public static String subPackage(String basePackage,int beginIndex){

                   return subHierarchicalPath(basePackage,".",".", beginIndex,-1);

         }

 

         public static String subPackage(String basePackage,int beginIndex,int endIndex){

                   return subHierarchicalPath(basePackage,".",".", beginIndex,endIndex);

         }

        

         public static String subPackage(String basePackage,String targetSplitChar,int beginIndex){

                   return subHierarchicalPath(basePackage,".", targetSplitChar ,beginIndex,-1);

         }

        

         public static String subPackage(String basePackage,String targetSplitChar,int beginIndex,int endIndex){

                   return subHierarchicalPath(basePackage,".", targetSplitChar ,beginIndex,endIndex);

         }

        

         public static String subHierarchicalPath(String basePackage,String sourceSplitChar,String targetSplitChar,int beginIndex){

                   return subHierarchicalPath(basePackage,".", targetSplitChar ,beginIndex,-1);

         }

 

         public static String subHierarchicalPath(String basePackage,String sourceSplitChar,String targetSplitChar,int beginIndex,int endIndex){

                   String[] packageNames = basePackage.split("[" + sourceSplitChar + "]");

                   StringBuilder subPackage = new StringBuilder();

                   if(endIndex >= 0 && endIndex <= beginIndex){

                            return null;

                   }

                   endIndex = endIndex < 0 || endIndex > packageNames.length?packageNames.length:endIndex;

                   for(;beginIndex < endIndex;beginIndex++){

                            subPackage.append(packageNames[beginIndex] + targetSplitChar);

                   }

                   subPackage.deleteCharAt(subPackage.length()-1);

                   return subPackage.toString();

         }

 

}
