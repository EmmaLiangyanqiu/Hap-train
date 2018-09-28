package hbi.sal.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import hbi.sal.mapper.ArCustomersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hbi.sal.dto.ArCustomers;
import hbi.sal.service.IArCustomersService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ArCustomersServiceImpl extends BaseServiceImpl<ArCustomers> implements IArCustomersService{

    @Autowired
    private ArCustomersMapper customersMapper;

    @Override
    public ArCustomers myQuery(Long customerId) {
        return customersMapper.myQuery(customerId);
    }
}