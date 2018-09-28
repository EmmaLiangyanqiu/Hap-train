package hbi.sal.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hbi.sal.dto.OmOrderLines;

public interface OmOrderLinesMapper extends Mapper<OmOrderLines>{
    public void myInsert(OmOrderLines orderLines);

}