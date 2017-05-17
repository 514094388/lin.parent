package lin.core.mvc.service.impl;

 

import java.io.BufferedWriter;

import java.io.File;

import java.io.FileWriter;

import java.io.IOException;

import java.sql.Connection;

import java.sql.DatabaseMetaData;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.sql.JDBCType;

import java.util.ArrayList;

import java.util.Collection;

import java.util.HashMap;

import java.util.HashSet;

import java.util.List;

import java.util.Map;

import java.util.Set;

import java.util.TreeSet;

 

import javax.sql.DataSource;

 

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

 

import lin.core.mvc.dao.IAutoToolsDao;

import lin.core.mvc.service.IAutoToolsService;

import lin.core.mvc.vo.autotools.QGenerateMVCFromTablesVo;

import lin.core.mvc.vo.autotools.QTableLinkListVo;

import lin.core.mvc.vo.autotools.QTableLinkVo;

import lin.core.mvc.vo.autotools.TableColumnsInfoVo;

import lin.core.mvc.vo.autotools.TableInfoVo;

import lin.core.util.JdbcTypes;

import lin.core.util.Names;

 

@Service

public class AutoToolsService implements IAutoToolsService {

        

         private static Logger logger = Logger.getLogger(AutoToolsService.class);

        

         //获取当前系统回车换行符

         private final String newLine = System.getProperty("line.separator");

        

         @Autowired

         private IAutoToolsDao iAutoToolsDao;

 

         @Autowired

         private DataSource dataSource;

 

         @Override

         public void generateMVCFromTabels(QGenerateMVCFromTablesVo qGenerateMVCFromTablesVo) throws Exception {

                   // TODO Auto-generated method stub

                   Map<String,List<List<QTableLinkVo>>> tableLinkGroupingMap = new HashMap<String,List<List<QTableLinkVo>>>();

                   tableLinkGrouping(tableLinkGroupingMap,qGenerateMVCFromTablesVo.getQTableLinkVoLists());

                  

                   Collection<List<List<QTableLinkVo>>> valuesCollection = tableLinkGroupingMap.values();

                   for(List<List<QTableLinkVo>> qTableLinkVoLists:valuesCollection){

                            generateMVCFromTabel(

                                               qGenerateMVCFromTablesVo.getSavePath(),

                                               qGenerateMVCFromTablesVo.getBasePackage(),

                                               qTableLinkVoLists);

                   }

         }

        

         private void tableLinkGrouping(Map<String, List<List<QTableLinkVo>>> tableLinkGroupingMap,

                            List<QTableLinkListVo> qTableLinkVoLists) {

                   // TODO Auto-generated method stub

                   for(QTableLinkListVo qTableLinkListVo : qTableLinkVoLists){

                            List<QTableLinkVo> qTableLinkVoList = qTableLinkListVo.getQTableLinkVoList();

                            for(QTableLinkVo qTableLinkVo:qTableLinkVoList){

                                     String table = qTableLinkVo.getTable();

                                     if(table != null){

                                               List<List<QTableLinkVo>> qTableLinkVoLists1 = tableLinkGroupingMap.get(table);

                                               if(qTableLinkVoLists1 == null){

                                                        qTableLinkVoLists1 = new ArrayList<List<QTableLinkVo>>();

                                                        qTableLinkVoLists1.add(qTableLinkVoList);

                                               }else{

                                                        qTableLinkVoLists1.add(qTableLinkVoList);

                                               }

                                               break;

                                     }

                            }

                   }

         }

 

         private void generateFile(String savePath,String content) throws IOException{

                   File file = new File(savePath);

                   if(!file.exists()){

                            File parentFile = file.getParentFile();

                            if(!parentFile.exists()){

                                     parentFile.mkdirs();

                            }

                            file.createNewFile();

                   }

                   BufferedWriter bw = new BufferedWriter(new FileWriter(new File(savePath)));

                   bw.write(content);

                   bw.flush();

                   bw.close();

         }

 

        

         public void generateMVCFromTabel(String savePath,String basePackage,List<List<QTableLinkVo>> qTableLinkVoLists) throws Exception {

                   // TODO Auto-generated method stub  

                   Set<String> tableNameSet = getTableNameList(qTableLinkVoLists);

                   //读取表结构信息

                   Map<String,TableInfoVo> tableInfoVoMap = getTableInfo(tableNameSet);

                  

                   for(List<QTableLinkVo> qTableLinkVoList:qTableLinkVoLists){

                           

                            //为每一个QTableLinkVo附加上TableInfoVo，并为同名列设置别名，同时返回表关联中的主表表信息对象

                            TableInfoVo primaryTableInfoVo = copyTableInfoVoObjectAndAddColumnAlias(qTableLinkVoList, tableInfoVoMap);

                           

                            generateVo(savePath, basePackage, qTableLinkVoList,primaryTableInfoVo);

                            generateController(savePath, basePackage,qTableLinkVoList,primaryTableInfoVo);

                            generateServiceInterface(savePath, basePackage,qTableLinkVoList,primaryTableInfoVo);

                            generateService(savePath, basePackage,qTableLinkVoList,primaryTableInfoVo);

                            generateDaoInterface(savePath, basePackage,qTableLinkVoList,primaryTableInfoVo);

                            generateDaoXML(savePath, basePackage,qTableLinkVoList,primaryTableInfoVo);

                   }

         }

        

         private Set<String> getTableNameList(List<List<QTableLinkVo>> qTableLinkVoLists) throws Exception {

                   // TODO Auto-generated method stub

                   Set<String> tableNameSet = new HashSet<String>();

                   for(List<QTableLinkVo> qTableLinkVoList : qTableLinkVoLists){

                            for(QTableLinkVo qTableLinkVo:qTableLinkVoList){

//                                  String tableName = getTableNameFromQTableLinkVo(qTableLinkVo);

//                                  tableNameSet.add(tableName);

                                     String tableName = qTableLinkVo.getTableName();

                                     if(tableName == null){

                                               throw new Exception("The QTableLinkVo object must has table!");

                                     }

                                     tableNameSet.add(tableName);

                            }

                   }

                   return tableNameSet;

         }

        

//      private String getTableNameFromQTableLinkVo(QTableLinkVo qTableLinkVo) throws Exception{

//               if(qTableLinkVo.getTable() != null){

//                         return qTableLinkVo.getTable();

//               }else if(qTableLinkVo.getInnerJoin() != null){

//                         return qTableLinkVo.getInnerJoin();

//               }else if(qTableLinkVo.getLeftJoin() != null){

//                         return qTableLinkVo.getLeftJoin();

//               }else if(qTableLinkVo.getRightJoin() != null){

//                         return qTableLinkVo.getRightJoin();

//               }else{

//                         throw new Exception("The QTableLinkVo object must has table!");

//               }

//      }

 

         private void generateDaoXML(String savePath, String basePackage, List<QTableLinkVo> qTableLinkVoList, TableInfoVo primaryTableInfoVo) throws IOException {

                   // TODO Auto-generated method stub

                   //获取主表的名称对象

                   Names names = primaryTableInfoVo.getNames();

                  

                   StringBuilder classContent = new StringBuilder();

                  

                   appendLine(classContent,"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

                   appendLine(classContent,"<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");

                   classContent.append(newLine);

                  

                   appendLine(classContent,"<mapper namespace=\"" + basePackage + ".dao." + names.daoInterfaceName + "\">");

                   classContent.append(newLine);

                  

                   generateDaoXMLSave(primaryTableInfoVo, classContent,names);

                   generateDaoXMLDelete(primaryTableInfoVo, classContent,names);

                   generateDaoXMLUpdate(primaryTableInfoVo, classContent,names);

                   generateDaoXMLSelect(primaryTableInfoVo, classContent,names,basePackage);

                  

                   appendLine(classContent,"</mapper>");

                   generateFile(

                                    savePath + "/" + names.resourceSaveType + "/" + Names.basePackageReplaceFromDotToDiagonal(basePackage) + "/dao/" + names.daoInterfaceName + names.xmlType,

                                     classContent.toString()

                                     );

         }

 

         private void generateDaoXMLSelect(TableInfoVo tableInfoVo, StringBuilder classContent,Names names,String basePackage) {

                   // TODO Auto-generated method stub

                   appendLine(classContent,"\t<select id=\"" + names.selectName + "\" resultType=\"" + basePackage + ".vo." + names.voName + "\">");

                   classContent.append(newLine);

                  

                   List<TableColumnsInfoVo> tableColumnsInfoVoList = tableInfoVo.getTableColumnsInfoVoList();

                   appendLine(classContent,"\t\tselect");

                  

                   StringBuilder selectColumns = new StringBuilder();

                   StringBuilder whereColumns = new StringBuilder();

                   generateSelectColumns(tableColumnsInfoVoList, selectColumns, whereColumns, names);

                  

                   classContent.append(selectColumns);

                   appendLine(classContent,"\t\tfrom " + tableInfoVo.getTableName());

                   appendLine(classContent,"\t\twhere 1 = 1");

                   classContent.append(whereColumns);

                  

                   appendLine(classContent,"\t</select>");

         }

        

 

         private String generateSelectColumns(List<TableColumnsInfoVo> tableColumnsInfoVoList, StringBuilder selectColumns,

                            StringBuilder whereColumns,Names names) {

                   String pkJavaName = null;

                   int index = 1;

                   for(int i=0;i<tableColumnsInfoVoList.size();i++){

                            TableColumnsInfoVo tableColumnsInfoVo = tableColumnsInfoVoList.get(i);

                            if(!tableColumnsInfoVo.isPrimaryKey()){

                                     appendLine(selectColumns,"\t\t<if test=\"" + names.selectColumnsXMLAlias + ".contains(" + index + ")\">");

                                     appendLine(selectColumns,"\t\t\t" + "," + tableColumnsInfoVo.getColumnName() + " as " + tableColumnsInfoVo.getColumnJavaName());

                                     appendLine(selectColumns,"\t\t</if>");

                                     index++;

                            }else{

                                     pkJavaName = tableColumnsInfoVo.getColumnJavaName();

                                     insertLine(0,selectColumns, "\t\t\t" + tableColumnsInfoVo.getColumnName() + " as " + tableColumnsInfoVo.getColumnJavaName());

                            }

 

                            appendLine(whereColumns,"\t\t<if test=\"" + names.qVoVarNameXMLAlias + "." + tableColumnsInfoVo.getColumnJavaName() + " != null\">");

                            appendLine(whereColumns,"\t\t\t" + "and " + tableColumnsInfoVo.getColumnName() + " = #{" + names.qVoVarNameXMLAlias + "." + tableColumnsInfoVo.getColumnJavaName() + ",jdbcType=" + tableColumnsInfoVo.getJdbcType() + "}");

                            appendLine(whereColumns,"\t\t</if>");

                   }

                   return pkJavaName;

         }

 

         private void generateDaoXMLUpdate(TableInfoVo tableInfoVo, StringBuilder classContent,Names names) {

                   // TODO Auto-generated method stub

                   appendLine(classContent,"\t<update id=\"" + names.updateName + "\">");

                   classContent.append(newLine);

                  

                   List<TableColumnsInfoVo> tableColumnsInfoVoList = tableInfoVo.getTableColumnsInfoVoList();

                   appendLine(classContent,"\t\tupdate " + tableInfoVo.getTableName());

                   appendLine(classContent,"\t\tset " + tableInfoVo.getTableName());

                  

                   StringBuilder columns = new StringBuilder();

                   TableColumnsInfoVo pkTableColumnsInfoVo =

                                     generateUpdateColumns(names, tableColumnsInfoVoList, columns);

                  

                   classContent.append(columns);

                  

                   appendLine(classContent,

                                     "\t\twhere " + pkTableColumnsInfoVo.getColumnName() +

                                     " = #{" + names.qVoVarNameXMLAlias + "." + pkTableColumnsInfoVo.getColumnJavaName() + ",jdbcType=" + pkTableColumnsInfoVo.getJdbcType() + "}"

                                     );

                   appendLine(classContent,"\t</update>");

         }

 

         private TableColumnsInfoVo generateUpdateColumns(Names names, List<TableColumnsInfoVo> tableColumnsInfoVoList,

                            StringBuilder columns) {

                   TableColumnsInfoVo pkTableColumnsInfoVo = null;

                   for(int i=0;i<tableColumnsInfoVoList.size();i++){

                            TableColumnsInfoVo tableColumnsInfoVo = tableColumnsInfoVoList.get(i);

                            if(!tableColumnsInfoVo.isPrimaryKey()){

                                     appendLine(columns,"\t\t<if test=\"" + names.qVoVarNameXMLAlias + "." + tableColumnsInfoVo.getColumnJavaName() + " != null\">");

                                     appendLine(columns,"\t\t\t" + "," + tableColumnsInfoVo.getColumnName() + "=#{" + names.qVoVarNameXMLAlias + "." + tableColumnsInfoVo.getColumnJavaName() + ",jdbcType=" + tableColumnsInfoVo.getJdbcType() + "}");

                                     appendLine(columns,"\t\t</if>");

                            }else{

                                     pkTableColumnsInfoVo = tableColumnsInfoVo;

                                     insertLine(0,columns, "\t\t\t" + tableColumnsInfoVo.getColumnName() + "=" + tableColumnsInfoVo.getColumnName());

                            }

                   }

                   return pkTableColumnsInfoVo;

         }

 

         private void generateDaoXMLDelete(TableInfoVo tableInfoVo, StringBuilder classContent,Names names) {

                   // TODO Auto-generated method stub

                   appendLine(classContent,"\t<delete id=\"" + names.deleteName + "\">");

                   classContent.append(newLine);

                  

                   List<TableColumnsInfoVo> tableColumnsInfoVoList = tableInfoVo.getTableColumnsInfoVoList();

                   appendLine(classContent,"\t\tdelete from " + tableInfoVo.getTableName());

                   appendLine(classContent,"\t\twhere 1 = 1 ");

                   for(int i=0;i<tableColumnsInfoVoList.size();i++){

                            TableColumnsInfoVo tableColumnsInfoVo = tableColumnsInfoVoList.get(i);

                           

                            appendLine(classContent,"\t\t<if test=\"" + names.qVoVarNameXMLAlias + "." + tableColumnsInfoVo.getColumnJavaName() + " != null\">");

                            appendLine(classContent,"\t\t\tand " + tableColumnsInfoVo.getColumnName() + " = #{" + names.qVoVarNameXMLAlias + "." + tableColumnsInfoVo.getColumnJavaName() + ",jdbcType=" + tableColumnsInfoVo.getJdbcType() + "}");

                            appendLine(classContent,"\t\t</if>");

                   }

                   appendLine(classContent,"\t</delete>");

         }

 

         private void generateDaoXMLSave(TableInfoVo tableInfoVo, StringBuilder classContent,Names names) {

 

                   List<TableColumnsInfoVo> tableColumnsInfoVoList = tableInfoVo.getTableColumnsInfoVoList();

 

                   StringBuilder columns = new StringBuilder();

                   StringBuilder columnsJava = new StringBuilder();

                   String pkJavaName = generateInsertColumns(tableColumnsInfoVoList, columns, columnsJava,names);

                  

                   appendLine(classContent,"\t<insert id=\"" + names.saveName + "\">");

                   classContent.append(newLine);

                  

                   appendLine(classContent,"\t\t<selectKey keyProperty=\"" + pkJavaName + "\"  resultType=\"Long\" order=\"BEFORE\">");

                   appendLine(classContent,"\t\t\tselect idseq.nextval from dual");

                   appendLine(classContent,"\t\t</selectKey>");

                   classContent.append(newLine);

 

                   appendLine(classContent,"\t\tinsert into " + tableInfoVo.getTableName() + "(");

                  

                   classContent.append(columns);

                  

                   appendLine(classContent,"\t\t)values(");

                  

                   classContent.append(columnsJava);

                  

                   appendLine(classContent,"\t\t)");

                   appendLine(classContent,"\t</insert>");

         }

 

         private String generateInsertColumns(List<TableColumnsInfoVo> tableColumnsInfoVoList, StringBuilder columns,

                            StringBuilder columnsJava,Names names) {

                   String pkJavaName = null;

                   for(int i=0;i<tableColumnsInfoVoList.size();i++){

                            TableColumnsInfoVo tableColumnsInfoVo = tableColumnsInfoVoList.get(i);

                            if(!tableColumnsInfoVo.isPrimaryKey()){

                                     appendLine(columns,"\t\t<if test=\"" + names.qVoVarNameXMLAlias + "." + tableColumnsInfoVo.getColumnJavaName() + " != null\">");

                                     appendLine(columns,"\t\t\t" + "," + tableColumnsInfoVo.getColumnName());

                                     appendLine(columns,"\t\t</if>");

        

                                     appendLine(columnsJava,"\t\t<if test=\"" + names.qVoVarNameXMLAlias + "." + tableColumnsInfoVo.getColumnJavaName() + " != null\">");

                                     appendLine(columnsJava,"\t\t\t" + ",#{" + names.qVoVarNameXMLAlias + "." + tableColumnsInfoVo.getColumnJavaName() + ",jdbcType=" + tableColumnsInfoVo.getJdbcType() + "}");

                                     appendLine(columnsJava,"\t\t</if>");

                            }else{

                                    

                                     pkJavaName = tableColumnsInfoVo.getColumnJavaName();

                                    

                                     insertLine(0,columns, "\t\t\t" + tableColumnsInfoVo.getColumnName());

                                    

                                      insertLine(0,columnsJava, "\t\t\t" + tableColumnsInfoVo.getColumnJavaName());

                            }

                   }

                   return pkJavaName;

         }

        

         private void appendLine(StringBuilder stringBuilder,String content){

                   stringBuilder.append(content);

                   stringBuilder.append(newLine);

         }

        

         private void insertLine(int fromIndex,StringBuilder stringBuilder,String content){

                   stringBuilder.insert(fromIndex, newLine);

                   stringBuilder.insert(fromIndex,content);

         }

 

         private void generateDaoInterface(String savePath, String basePackage, List<QTableLinkVo> qTableLinkVoList, TableInfoVo primaryTableInfoVo) throws IOException {

                   // TODO Auto-generated method stub

                  

                   Names names = primaryTableInfoVo.getNames();

 

                   StringBuilder classContent = new StringBuilder();

                  

                   appendLine(classContent,"package " + basePackage + ".dao;");

                   classContent.append(newLine);

 

                   Set<String> imports = new TreeSet<String>();

                   imports.add("import org.springframework.stereotype.Service;");

                   imports.add("import " + basePackage + ".vo." + names.voName + ";");

                   imports.add("import org.apache.ibatis.annotations.Param;");

                   imports.add("import java.util.Set;");

                   imports.add("import java.util.List;");

                   generateImports(imports, classContent);

                  

                   appendLine(classContent,"@Service");

                   appendLine(classContent,"public " + names.interfaceType + " " + names.daoInterfaceName + "{");

                   classContent.append(newLine);

                  

                   appendLine(classContent,"\tpublic void " + names.saveName + "(@Param(\"" + names.qVoVarNameXMLAlias + "\")" + names.voName + " " + names.qVoVarName + ");");

                   classContent.append(newLine);

                  

                   appendLine(classContent,"\tpublic void " + names.deleteName + "(@Param(\"" + names.qVoVarNameXMLAlias + "\")" + names.voName + " " + names.qVoVarName + ");");

                   classContent.append(newLine);

                  

                   appendLine(classContent,"\tpublic void " + names.updateName + " " + "(@Param(\"" + names.qVoVarNameXMLAlias + "\")" + names.voName + " " + names.qVoVarName + ");");

                   classContent.append(newLine);

                  

                   appendLine(classContent,"\tpublic List<" + names.voName + "> " + names.selectName + " " + "(@Param(\"" + names.qVoVarNameXMLAlias + "\")" + names.voName + " " + names.qVoVarName + ",@Param(\"" + names.selectColumnsXMLAlias + "\")Set<?> " + names.selectColumnsVarName + ");");

                   classContent.append(newLine);

                  

                   appendLine(classContent,"}");

                  

                   generateFile(

                                     savePath + "/" + names.javaSaveType + "/" + Names.basePackageReplaceFromDotToDiagonal(basePackage) + "/dao/" + names.daoInterfaceName + names.javaType,

                                     classContent.toString()

                                     );

         }

        

         private void generateImports(Set<String> imports,StringBuilder classContent){

                   for(String importStr : imports){

                            appendLine(classContent,importStr);

                   }

                   classContent.append(newLine);

         }

 

         private void generateService(String savePath, String basePackage,List<QTableLinkVo> qTableLinkVoList, TableInfoVo primaryTableInfoVo) throws IOException {

                   // TODO Auto-generated method stub

                  

                   Names names = primaryTableInfoVo.getNames();

                  

                   Set<String> imports = new TreeSet<String>();

 

                   StringBuilder classContent = new StringBuilder();

                   appendLine(classContent,"package " + basePackage + ".service.impl;");

                   classContent.append(newLine);

 

                   imports.add("import org.springframework.stereotype.Service;");

                   imports.add("import org.springframework.beans.factory.annotation.Autowired;");

                   imports.add("import " + basePackage + ".vo." + names.voName + ";");

                   imports.add("import " + basePackage + ".service." + names.serviceInterfaceName + ";");

                   imports.add("import " + basePackage + ".dao." + names.daoInterfaceName + ";");

                   imports.add("import java.util.Set;");

                   imports.add("import java.util.List;");

                  

                   generateImports(imports,classContent);

                  

                   appendLine(classContent,"@Service");

                   appendLine(classContent,"public class " + names.serviceName + " implements " + names.serviceInterfaceName + " {");

                   classContent.append(newLine);

                  

                   appendLine(classContent,"\t@Autowired");

                   appendLine(classContent,"\tprivate " + names.daoInterfaceName + " " + names.daoVarName + ";");

                   classContent.append(newLine);

                  

                   generateServiceSave(names, classContent);

                  

                   generateServiceDelete(names, classContent);

                  

                   generateServiceUpdate(names, classContent);

                  

                   generateServiceSelect(names, classContent);

                  

                   appendLine(classContent,"}");

                  

                   generateFile(

                                     savePath + "/" + names.javaSaveType + "/" + Names.basePackageReplaceFromDotToDiagonal(basePackage) + "/service/impl/" + names.serviceName + names.javaType,

                                     classContent.toString()

                                     );

         }

 

         private void generateServiceSelect(Names names, StringBuilder classContent) {

                   appendLine(classContent,"\tpublic List<" + names.voName + "> " + names.selectName + "(" + names.voName + " " + names.qVoVarName + ",Set<?> " + names.selectColumnsVarName + "){");

                   classContent.append(newLine);

                   appendLine(classContent,"\t\treturn " + names.daoVarName + "." + names.selectName + "(" + names.qVoVarName + "," + names.selectColumnsVarName + ");");

                   classContent.append(newLine);

                  

                   appendLine(classContent,"\t}");

                   classContent.append(newLine);

         }

 

         private void generateServiceUpdate(Names names, StringBuilder classContent) {

                   appendLine(classContent,"\tpublic void " + names.updateName + "(" + names.voName + " " + names.qVoVarName + "){");

                   classContent.append(newLine);

                   appendLine(classContent,"\t\t" + names.daoVarName + "." + names.updateName + "(" + names.qVoVarName + ");");

                   classContent.append(newLine);

                  

                   appendLine(classContent,"\t}");

                   classContent.append(newLine);

         }

 

         private void generateServiceDelete(Names names, StringBuilder classContent) {

                   appendLine(classContent,"\tpublic void " + names.deleteName + "(" + names.voName + " " + names.qVoVarName + "){");

                   classContent.append(newLine);

                   appendLine(classContent,"\t\t" + names.daoVarName + "." + names.deleteName + "(" + names.qVoVarName + ");");

                   classContent.append(newLine);

                  

                   appendLine(classContent,"\t}");

                   classContent.append(newLine);

         }

 

         private void generateServiceSave(Names names, StringBuilder classContent) {

                   appendLine(classContent,"\tpublic void " + names.saveName + "(" + names.voName + " " + names.qVoVarName + "){");

                   classContent.append(newLine);

                   appendLine(classContent,"\t\t" + names.daoVarName + "." + names.saveName + "(" + names.qVoVarName + ");");

                   classContent.append(newLine);

                  

                   appendLine(classContent,"\t}");

                   classContent.append(newLine);

         }

 

         private void generateServiceInterface(String savePath, String basePackage,List<QTableLinkVo> qTableLinkVoList, TableInfoVo primaryTableInfoVo) throws IOException {

                   // TODO Auto-generated method stub

                  

                   Names names = primaryTableInfoVo.getNames();

                  

                   StringBuilder classContent = new StringBuilder();

                   appendLine(classContent,"package " + basePackage + ".service;");

                   classContent.append(newLine);

 

                   Set<String> imports = new TreeSet<String>();

                   imports.add("import " + basePackage + ".vo." + names.voName + ";");

                   imports.add("import java.util.Set;");

                   imports.add("import java.util.List;");

                   generateImports(imports, classContent);

                  

                   appendLine(classContent,"public interface " + names.serviceInterfaceName  + " {");

                   classContent.append(newLine);

                  

                   appendLine(classContent,"\tpublic void " + names.saveName + "(" + names.voName + " " + names.qVoVarName + ");");

                   classContent.append(newLine);

                  

 

                   appendLine(classContent,"\tpublic void " + names.deleteName + "(" + names.voName + " " + names.qVoVarName + ");");

                   classContent.append(newLine);

                  

                   appendLine(classContent,"\tpublic void " + names.updateName + "(" + names.voName + " " + names.qVoVarName + ");");

                   classContent.append(newLine);

                  

                   appendLine(classContent,"\tpublic List<" + names.voName + "> " + names.selectName + "(" + names.voName + " " + names.qVoVarName + ",Set<?> " + names.selectColumnsVarName + ");");

                   classContent.append(newLine);

                  

                   appendLine(classContent,"}");

                  

                   generateFile(

                                     savePath + "/" + names.javaSaveType + "/" + Names.basePackageReplaceFromDotToDiagonal(basePackage) + "/service/" + names.serviceInterfaceName + names.javaType,

                                     classContent.toString()

                                     );

         }

 

         private void generateVo(String savePath, String basePackage, List<QTableLinkVo> qTableLinkVoList, TableInfoVo primaryTableInfoVo) throws IOException {

                   // TODO Auto-generated method stub

 

                   StringBuilder classContent = new StringBuilder();

                   appendLine(classContent,"package " + basePackage + ".vo;");

                   classContent.append(newLine);

                  

                   Set<String> imports = new TreeSet<String>();

                   imports.add("import lombok.Data;");

                   imports.add("import io.swagger.annotations.ApiModel;");

                   imports.add("import io.swagger.annotations.ApiModelProperty;");

                  

                   StringBuilder properties = new StringBuilder();

                   for(QTableLinkVo qTableLinkVo:qTableLinkVoList){

                           

                            TableInfoVo tableInfoVo = qTableLinkVo.getTableInfoVo();

                            List<TableColumnsInfoVo> tableColumnsInfoVoList = tableInfoVo.getTableColumnsInfoVoList();

                           

                            for(TableColumnsInfoVo tableColumnsInfoVo:tableColumnsInfoVoList){

                                     Class<?> typeClass = tableColumnsInfoVo.getJavaClass();

                                     String typeClassName = typeClass.getName();

                                      imports.add("import " + typeClassName + ";");

                                    

                                     appendLine(properties,"@ApiModelProperty(value = \"" + tableColumnsInfoVo.getComments() + "\")");

                                     appendLine(properties,"private " + typeClass.getSimpleName() + " " + tableColumnsInfoVo.getColumnJavaName() + ";");

                                     properties.append(newLine);

                            }

                   }

                   generateImports(imports, classContent);

                  

                   Names names = primaryTableInfoVo.getNames();

                  

                   appendLine(classContent,"@Data");

                   appendLine(classContent,"@ApiModel(value=\"" + names.voName + "\",description=\"" + primaryTableInfoVo.getComments() + "--传入传出参数Vo类\")");

                   appendLine(classContent,"public class " + names.voName + "{");

                   classContent.append(newLine);

                  

                   classContent.append(properties);

                  

                   appendLine(classContent,"}");

                  

                   generateFile(savePath + "/" + names.javaSaveType + "/" + Names.basePackageReplaceFromDotToDiagonal(basePackage) + "/vo/" + names.voName + names.javaType,classContent.toString());

         }

 

         private TableInfoVo copyTableInfoVoObjectAndAddColumnAlias(List<QTableLinkVo> qTableLinkVoList,

                            Map<String, TableInfoVo> tableInfoVoMap) throws Exception {

                  

                   //读取出关联表中的主表，并作为当前方法的返回值

                   TableInfoVo primaryTableInfoVo = null;

                  

                   //判断表关联入参vo列表是否为空

                   if(qTableLinkVoList != null && qTableLinkVoList.size() != 0){

                            //创建存放字段对应表Map关联对象

                            Map<String,Integer> columnTableMap = new HashMap<String,Integer>();

                            //保存所有表对应字段到Map对象中

                            Map<String,TableColumnsInfoVo> tableColumnsInfoVoMap = new HashMap<String,TableColumnsInfoVo>();

                           

                            //生成表关联vo入参对象列表中的表信息副本

                            for(int i=0;i<qTableLinkVoList.size();i++){

                                     //读取表关联vo入参对象列表中的表对象

                                     QTableLinkVo qTableLinkVo = qTableLinkVoList.get(i);

                                     //生成一个表信息对象副本

                                     TableInfoVo tableInfoVo = tableInfoVoMap.get(qTableLinkVo.getTableName()).copy();

                                     //将表信息副本set到表关联对象vo中

                                     qTableLinkVo.setTableInfoVo(tableInfoVo);

                                    

                                     //获取主表的表信息

                                     if(primaryTableInfoVo == null && tableInfoVo != null){

                                               primaryTableInfoVo = tableInfoVo;

                                     }

                                    

                                     //读取出表字段信息列表

                                     List<TableColumnsInfoVo> tableColumnsInfoVoList = tableInfoVo.getTableColumnsInfoVoList();

                                     //判断所有关联表中的字段有没有重名，如果存在重名，则用表别名为表字段生成别名

                                     for(TableColumnsInfoVo tableColumnsInfoVo:tableColumnsInfoVoList){

                                              

                                               String columnName = tableColumnsInfoVo.getColumnName();

                                              

                                               if(!columnTableMap.containsKey(columnName)){

                                                       

                                                        columnTableMap.put(columnName, i);

                                                        tableColumnsInfoVoMap.put(columnName, tableColumnsInfoVo);

                                                       

                                               }else{

                                                       

                                                        String tableAlias = qTableLinkVo.getAlias();

                                                       

                                                        tableColumnsInfoVoMap.get(columnName).setAlias(tableAlias);

                                                        tableColumnsInfoVo.setAlias(tableAlias);

                                               }

                                     }

                                    

                            }

                   }

                   if(primaryTableInfoVo == null){

                            throw new Exception("表关联必须指定主表");

                   }

                   return primaryTableInfoVo;

         }

         private void generateController(String savePath, String basePackage, List<QTableLinkVo> qTableLinkVoList, TableInfoVo primaryTableInfoVo) throws IOException {

                   /*

                   * 生成Controller文件

                   */

                   StringBuilder classContent = new StringBuilder();

                   appendLine(classContent,"package " + basePackage + ".controller;");

                   classContent.append(newLine);

                  

                   Names names = primaryTableInfoVo.getNames();

                  

                   //生成Controller类的Import代码

                   generateControllerImport(basePackage, names, classContent);

                  

                   appendLine(classContent,"/**");

                   appendLine(classContent," * Table Comments:" + primaryTableInfoVo.getComments());

                   appendLine(classContent," * --接口发布类");

                   appendLine(classContent," * @author LIN");

                   appendLine(classContent," **/");

                  

                   //设置服务类型

                   appendLine(classContent,"@Api(value=\"" + primaryTableInfoVo.getComments() + "--接口发布类\")");

                   appendLine(classContent,"@RestController");

                   String[] packageNames = basePackage.split("[.]");

                   String mapping = Names.subPackage(basePackage,"/",packageNames.length-1);

                   appendLine(classContent,"@RequestMapping(\"/api/" + mapping + "/" + Names.firstCharToUpOrToLow(1, names.tableJaveName) + "\")");

                   appendLine(classContent,"public class " + names.controllerName + " extends AbstractControllerBase {");

                   classContent.append(newLine);

                  

                   //注入service类

                   appendLine(classContent,"\t@Autowired");

                   appendLine(classContent,"\tprivate " + names.serviceInterfaceName + " " + names.serviceVarName + ";");

                   classContent.append(newLine);

                  

                   String tableName = primaryTableInfoVo.getTableName();

                  

                   //生成保存数据服务

                   generateControllerSave(tableName, primaryTableInfoVo, names, classContent);

                  

                   //生成删除数据服务

                   generateControllerDelete(tableName, primaryTableInfoVo, names, classContent);

                  

                   //生成修改数据服务

                   generateControllerUpdate(tableName, primaryTableInfoVo, names, classContent);

                  

                   //生成查询数据服务

                   generateControllerSelect(tableName, primaryTableInfoVo, names, classContent);

                  

                   appendLine(classContent,"}");

                   generateFile(

                                     savePath + "/" + names.javaSaveType + "/" + Names.basePackageReplaceFromDotToDiagonal(basePackage) + "/controller/" + names.controllerName + names.javaType,

                                     classContent.toString());

         }

         /**

         * 生成Controller类的import

         * @param basePackage

         * @param names

         * @param classContent

         */

         private void generateControllerImport(String basePackage, Names names, StringBuilder classContent) {

                   appendLine(classContent,"import java.util.List;");

                   appendLine(classContent,"import java.util.ArrayList;");

                   appendLine(classContent,"import java.util.Set;");

                   appendLine(classContent,"import java.util.HashSet;");

                   appendLine(classContent,"import org.springframework.beans.factory.annotation.Autowired;");

                   appendLine(classContent,"import org.springframework.web.bind.annotation.RestController;");

                   appendLine(classContent,"import org.springframework.web.bind.annotation.RequestMapping;");

                   appendLine(classContent,"import org.springframework.web.bind.annotation.PostMapping;");

                   appendLine(classContent,"import org.springframework.web.bind.annotation.RequestBody;");

                   appendLine(classContent,"import io.swagger.annotations.Api;");

                   appendLine(classContent,"import io.swagger.annotations.ApiOperation;");

                   appendLine(classContent,"import lin.core.common.AbstractControllerBase;");

                   appendLine(classContent,"import lin.core.common.ResponseVo;");

                   appendLine(classContent,"import " + basePackage + ".service." + names.serviceInterfaceName + ";");

                   appendLine(classContent,"import " + basePackage + ".vo." + names.voName + ";");

                   classContent.append(newLine);

         }

 

 

         private void generateControllerSelect(String tableName, TableInfoVo tableInfoVo, Names names,

                            StringBuilder classContent) {

                   appendLine(classContent,"\t@ApiOperation(\"删除" + tableName + "表(" + tableInfoVo.getComments() + ")数据" + "\")");

                   appendLine(classContent,"\t@PostMapping(\"/select\")");

                   appendLine(classContent,"\tpublic ResponseVo<List<List<" + names.voName + ">>> " + names.selectName + "(@RequestBody List<" + names.voName + "> " + names.qVoListVarName + "){");

                   classContent.append(newLine);

                  

                   appendLine(classContent,"\t\tList<List<" + names.voName + ">> " + names.rVoVarName + "Lists = new ArrayList<List<" + names.voName + ">>();");

                   classContent.append(newLine);

                  

                   appendLine(classContent,"\t\ttry{");

                   appendLine(classContent,"\t\t\tSet<Integer> " + names.selectColumnsVarName + " = new HashSet<Integer>();");

                   int columnsSize = tableInfoVo.getTableColumnsInfoVoList().size();

                   for(int i=1;i<=columnsSize;i++){

                            appendLine(classContent,"\t\t\t" + names.selectColumnsVarName + ".add(" + i + ");");

                   }

                   appendLine(classContent,"\t\t\tfor(" + names.voName + " " + names.qVoVarName + ":" + names.qVoListVarName + "){");

                   appendLine(classContent,"\t\t\t\t" + names.rVoVarName + "Lists.add(" + names.serviceVarName + "." + names.selectName + "(" + names.qVoVarName + "," + names.selectColumnsVarName + "));");

                   appendLine(classContent,"\t\t\t}");

                   appendLine(classContent,"\t\t}catch(Exception e){");

                   appendLine(classContent,"\t\t\te.getStackTrace();");

                   appendLine(classContent,"\t\t\treturn error(500,e.getMessage());");

                   appendLine(classContent,"\t\t}");

                   appendLine(classContent,"\t\treturn response(" + names.rVoVarName + "Lists);");

                   appendLine(classContent,"\t}");

                   classContent.append(newLine);

         }

        

         private void generateControllerUpdate(String tableName, TableInfoVo tableInfoVo, Names names,

                            StringBuilder classContent) {

                   appendLine(classContent,"\t@ApiOperation(\"修改" + tableName + "表(" + tableInfoVo.getComments() + ")数据" + "\")");

                   appendLine(classContent,"\t@PostMapping(\"/" + names.updateName + "\")");

                   appendLine(classContent,"\tpublic ResponseVo<?> " + names.updateName + "(@RequestBody List<" + names.voName + "> " + names.qVoListVarName + "){");

                   classContent.append(newLine);

                  

                   appendLine(classContent,"\t\ttry{");

                   appendLine(classContent,"\t\t\tfor(" + names.voName + " " + names.qVoVarName + ":" + names.qVoListVarName + "){");

                   appendLine(classContent,"\t\t\t\t" + names.serviceVarName + "." + names.updateName + "(" + names.qVoVarName + ");");

                   appendLine(classContent,"\t\t\t}");

                   appendLine(classContent,"\t\t}catch(Exception e){");

                   appendLine(classContent,"\t\t\te.getStackTrace();");

                   appendLine(classContent,"\t\t\treturn error(500,e.getMessage());");

                   appendLine(classContent,"\t\t}");

                   appendLine(classContent,"\t\treturn response();");

                   appendLine(classContent,"\t}");

                   classContent.append(newLine);

         }

        

         private void generateControllerDelete(String tableName, TableInfoVo tableInfoVo, Names names,

                            StringBuilder classContent) {

                   appendLine(classContent,"\t@ApiOperation(\"删除" + tableName + "表(" + tableInfoVo.getComments() + ")数据" + "\")");

                   appendLine(classContent,"\t@PostMapping(\"/" + names.deleteName + "\")");

                appendLine(classContent,"\tpublic ResponseVo<?> " + names.deleteName + "(@RequestBody List<" + names.voName + "> " + names.qVoListVarName + "){");

                   classContent.append(newLine);

                  

                   appendLine(classContent,"\t\ttry{");

                   appendLine(classContent,"\t\t\tfor(" + names.voName + " " + names.qVoVarName + ":" + names.qVoListVarName + "){");

                   appendLine(classContent,"\t\t\t\t" + names.serviceVarName + "." + names.deleteName + "(" + names.qVoVarName + ");");

                   appendLine(classContent,"\t\t\t}");

                   appendLine(classContent,"\t\t}catch(Exception e){");

                   appendLine(classContent,"\t\t\te.getStackTrace();");

                   appendLine(classContent,"\t\t\treturn error(500,e.getMessage());");

                   appendLine(classContent,"\t\t}");

                  

                   appendLine(classContent,"\t\treturn response();");

                   appendLine(classContent,"\t}");

                   classContent.append(newLine);

         }

 

         private void generateControllerSave(String tableName, TableInfoVo tableInfoVo, Names names,

                            StringBuilder classContent) {

                   appendLine(classContent,"\t@ApiOperation(\"保存" + tableName + "表(" + tableInfoVo.getComments() + ")数据" + "\")");

                   appendLine(classContent,"\t@PostMapping(\"/" + names.saveName + "\")");

                   appendLine(classContent,"\tpublic ResponseVo<?> " + names.saveName + "(@RequestBody List<" + names.voName + "> " + names.qVoListVarName + "){");

                   classContent.append(newLine);

                  

                   appendLine(classContent,"\t\ttry{");

                   appendLine(classContent,"\t\t\tfor(" + names.voName + " " + names.qVoVarName + ":" + names.qVoListVarName + "){");

                   appendLine(classContent,"\t\t\t\t" + names.serviceVarName + "." + names.saveName + "(" + names.qVoVarName + ");");

                   appendLine(classContent,"\t\t\t}");

                   appendLine(classContent,"\t\t}catch(Exception e){");

                   appendLine(classContent,"\t\t\te.getStackTrace();");

                   appendLine(classContent,"\t\t\treturn error(500,e.getMessage());");

                   appendLine(classContent,"\t\t}");

                   appendLine(classContent,"\t\treturn response();");

                   appendLine(classContent,"\t}");

                   classContent.append(newLine);

         }

        

         private Map<String,TableInfoVo> getTableInfo(Set<String> tableNameSet) throws SQLException{

                  

                   //获取表名和表备注信息

                   Map<String,TableInfoVo> tableInfoVoMap = iAutoToolsDao.getTableComments(tableNameSet);

                  

                   Collection<TableInfoVo> tableInfoVos = tableInfoVoMap.values();

                   for(TableInfoVo tableInfoVo:tableInfoVos){

                            //生成MVC类名、变量名

                            tableInfoVo.setNames(new Names(tableInfoVo.getTableName()));

                            //获取表字段信息

                            tableInfoVo.setTableColumnsInfoVoList(getTableColumnsInfo(tableInfoVo.getTableName()));

                   }

                   return tableInfoVoMap;

         }

        

         private List<TableColumnsInfoVo> getTableColumnsInfo(String tableName) throws SQLException{

                  

                   List<TableColumnsInfoVo> tableColumnsInfoVoList = new ArrayList<TableColumnsInfoVo>();

 

                   Connection connection = dataSource.getConnection();

                   DatabaseMetaData databaseMetaData = connection.getMetaData();

                  

                   ResultSet pkResultSet = databaseMetaData.getPrimaryKeys(null, null, tableName);

//               testPkResultSet(pkResultSet);

        Set<String> pkSet = new HashSet<String>();

        while(pkResultSet.next()){

                 pkSet.add(pkResultSet.getString("COLUMN_NAME"));

        }

       

                   Map<String,TableColumnsInfoVo> commentsMap = iAutoToolsDao.getColumnsComments(tableName);

                  

                   ResultSet resultSet = databaseMetaData.getColumns(null, null, tableName, null);

//               testDatabaseMetaData(resultSet);

                   while(resultSet.next()){

                            TableColumnsInfoVo tableColumnsInfoVo = new TableColumnsInfoVo();

                            String columnName = resultSet.getString("COLUMN_NAME");

            tableColumnsInfoVo.setColumnName(columnName);//列名

            tableColumnsInfoVo.setPrimaryKey(pkSet.contains(columnName));

            tableColumnsInfoVo.setTypeName(resultSet.getString("TYPE_NAME"));//字段数据类型(对应java.sql.Types中的常量) 

            tableColumnsInfoVo.setDataType(resultSet.getInt("DATA_TYPE"));

            tableColumnsInfoVo.setComments(commentsMap.get(columnName).getComments());

            tableColumnsInfoVoList.add(tableColumnsInfoVo);

                   }

                  

                   return tableColumnsInfoVoList;

         }

        

//      private void testPkResultSet(ResultSet pkResultSet) {

//               // TODO Auto-generated method stub

//               logger.info("ResultSetMetaData:");

//               try {

//                         ResultSetMetaData resultSetMetaData = pkResultSet.getMetaData();

//                         while(pkResultSet.next()){

//                                  int columns = resultSetMetaData.getColumnCount();

//                                  for(int i=1;i<=columns;i++){

//                                           logger.info("ColumnName:" + resultSetMetaData.getColumnName(i) + ":" + pkResultSet.getString(resultSetMetaData.getColumnName(i)));

//                                  }

//                         }

//               } catch (SQLException e) {

//                         // TODO Auto-generated catch block

//                         logger.error(e.getMessage());

//               }

//      }

 

//      private void testDatabaseMetaData(ResultSet resultSet){

//               logger.info("ResultSetMetaData:");

//               try {

//                         ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

//                         while(resultSet.next()){

//                                  int columns = resultSetMetaData.getColumnCount();

//                                  for(int i=1;i<=columns;i++){

//                                           logger.info("ColumnName:" + resultSetMetaData.getColumnName(i) + ":" + resultSet.getString(resultSetMetaData.getColumnName(i)));

//                                  }

//                         }

//               } catch (SQLException e) {

//                         // TODO Auto-generated catch block

//                         logger.error(e.getMessage());

//               }

//              

//      }

 

}
