package lin.core.common;

 

import java.io.Serializable;

 

import io.swagger.annotations.ApiModel;

import io.swagger.annotations.ApiModelProperty;

import lombok.Data;

 

 

@Data

@ApiModel(value="PageVo value",description="PageVo description")

public class PageVo<T> implements Serializable{

 

         /**

         *

          */

         private static final long serialVersionUID = 8697904815027792847L;

        

         @ApiModelProperty(value="总共条数",required=true)

         private Long total;

        

         @ApiModelProperty(value="当前页面,与size一起使用,以size为准,默认为0",required=true)

         private long page;

        

         @ApiModelProperty(value="每页条数,不传或传0则返回所有数据",required=true)

         private long size;

        

         private Long start;

        

         private Long end;

        

         private T data;

        

         public PageVo(){

                  

         }

        

         public PageVo(PageVo<?> pageVo){

                   this.page = pageVo.getPage();

                   this.size = pageVo.getSize();

                  

         }

        

         public long getStart() {

                   if( start == null){

                                     start = (page-1)*size+1;

                   }

                   return  start;

         }

         public long getEnd() {

                   if( end == null){

                            end = (page)*size;

                   }

                   return  end;

         }

}
