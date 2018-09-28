package hbi.demo.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hbi.demo.dto.DemoBank;

import java.util.List;

public interface IDemoBankService extends IBaseService<DemoBank>, ProxySelf<IDemoBankService>{

    /*
    * 批量删除
    * */
    int myBatchDelete(IRequest request, List<DemoBank> list) throws Exception;

    /*
    * 保存
    * */
    List<DemoBank> myBatchSubmit(IRequest request, List<DemoBank> list);
    public void test();
}