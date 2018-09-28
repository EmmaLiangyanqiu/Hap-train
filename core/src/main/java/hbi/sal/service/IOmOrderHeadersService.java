package hbi.sal.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hbi.demo.dto.DemoBank;
import hbi.sal.dto.OmOrderHeaders;

import java.util.List;

public interface IOmOrderHeadersService extends IBaseService<OmOrderHeaders>, ProxySelf<IOmOrderHeadersService> {
    /*
    * 保存
    * */
    public List<OmOrderHeaders> myBatchSubmit(IRequest request, List<OmOrderHeaders> list);

    /*
    * 查询
    * */
    public List<OmOrderHeaders> myQuery(Long inventoryItemId,IRequest request,OmOrderHeaders dto,int page,int pageSize);
    /*
    * 删除
    * */
    public void myDelete(IRequest request,List<OmOrderHeaders> list) throws Exception;
}