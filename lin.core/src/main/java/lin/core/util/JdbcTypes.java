package lin.core.util;

 

import java.math.BigDecimal;

import java.sql.Array;

import java.sql.Blob;

import java.sql.Clob;

import java.sql.Ref;

import java.sql.ResultSet;

import java.sql.Struct;

import java.time.ZonedDateTime;

 

public enum JdbcTypes {

        

         BIT(Boolean.class),

        

         TINYINT(Byte.class),

        

         SMALLINT(Short.class),

        

         INTEGER(Integer.class),

        

         BIGINT(Long.class),

        

         FLOAT(Double.class),

        

         REAL(Float.class),

        

         DOUBLE(Double.class),

        

         NUMBER(BigDecimal.class),

        

         NUMERIC(BigDecimal.class),

        

         DECIMAL(BigDecimal.class),

        

         CHAR(String.class),

        

         VARCHAR(String.class),

        

         LONGVARCHAR(String.class),

        

         DATE(java.util.Date.class),

        

         TIME(java.util.Date.class),

        

         TIMESTAMP(java.util.Date.class),

        

         BINARY(byte[].class),

        

         VARBINARY(byte[].class),

        

         LONGVARBINARY(byte[].class),

        

         NULL(String.class),

        

         OTHER(Object.class),

        

         JAVA_OBJECT(Object.class),

        

         DISTINCT(String.class),

        

         STRUCT(Struct.class),

        

         ARRAY(Array.class),

        

         BLOB(Blob.class),

        

         CLOB(Clob.class),

        

         REF(Ref.class),

        

         DATALINK(String.class),

        

         BOOLEAN(Boolean.class),

        

         ROWID(String.class),

        

         NCHAR(String.class),

        

         NVARCHAR(String.class),

        

         LONGNVARCHAR(String.class),

        

         NCLOB(Clob.class),

        

         SQLXML(String.class),

        

         REF_CURSOR(ResultSet.class),

        

         TIME_WITH_TIMEZONE(ZonedDateTime.class),

        

         TIMESTAMP_WITH_TIMEZONE(ZonedDateTime.class);

        

         private Class cla;

        

         JdbcTypes(Class cla){

                   this.cla = cla;

         }

        

         public Class getJavaClass(){

                   return this.cla;

         }

 

}
