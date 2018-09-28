package hbi.sal.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hbi.sal.dto.ArCustomers;

public interface IArCustomersService extends IBaseService<ArCustomers>, ProxySelf<IArCustomersService>{

    public ArCustomers myQuery(Long customerId);
}