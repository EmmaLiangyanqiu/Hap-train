package hbi.sal.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hbi.sal.dto.OmOrderHeaders;
import hbi.sal.dto.OmOrderLines;

import java.util.List;

public interface IOmOrderLinesService extends IBaseService<OmOrderLines>, ProxySelf<IOmOrderLinesService> {
    /*
    * 添加行
    * */
    public void myInsert(OmOrderLines orderLines);
    /*
    * 查询
    * */
    public List<OmOrderLines> myQuery(IRequest request, OmOrderLines dto, int page, int pageSize);
}