package hbi.sal.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hbi.sal.dto.OmOrderHeaders;

public interface OmOrderHeadersMapper extends Mapper<OmOrderHeaders>{
    /*
    * 提交
    * */
    public void myInsert(OmOrderHeaders orderHeaders);
    /*
    * 根据id查询
    * */
   public OmOrderHeaders myQueryById(Long headerId);
}