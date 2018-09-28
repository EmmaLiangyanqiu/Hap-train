package hbi.sal.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import hbi.sal.mapper.InvInventoryItemsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hbi.sal.dto.InvInventoryItems;
import hbi.sal.service.IInvInventoryItemsService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class InvInventoryItemsServiceImpl extends BaseServiceImpl<InvInventoryItems> implements IInvInventoryItemsService{

    @Autowired
    private InvInventoryItemsMapper itemsMapper;

    @Override
    public InvInventoryItems myQuery(String itemCode) {
        return itemsMapper.myQuery(itemCode);
    }
}