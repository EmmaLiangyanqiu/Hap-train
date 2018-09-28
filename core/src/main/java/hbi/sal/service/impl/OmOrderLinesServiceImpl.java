package hbi.sal.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hbi.sal.dto.OmOrderHeaders;
import hbi.sal.mapper.InvInventoryItemsMapper;
import hbi.sal.mapper.OmOrderLinesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hbi.sal.dto.OmOrderLines;
import hbi.sal.service.IOmOrderLinesService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class OmOrderLinesServiceImpl extends BaseServiceImpl<OmOrderLines> implements IOmOrderLinesService{

    @Autowired
    private IOmOrderLinesService orderLinesService;
    @Autowired
    private OmOrderLinesMapper orderLinesMapper;
    @Autowired
    private InvInventoryItemsMapper itemsMapper;
    /*
    * 添加行
    * */
    @Override
    public void myInsert(OmOrderLines orderLines) {
        orderLinesMapper.myInsert(orderLines);
    }

    /*
    * 查询
    * */
    @Override
    public List<OmOrderLines> myQuery(IRequest request, OmOrderLines dto, int page, int pageSize) {
        List<OmOrderLines> orderLinesList=orderLinesService.select(request,dto,page,pageSize);

        if (orderLinesList!=null||orderLinesList.size()!=0){
            for (int i = 0; i <orderLinesList.size() ; i++) {
                OmOrderLines orderLines=orderLinesList.get(i);
                //查询物料名称
                String itemCode=itemsMapper.myQueryById(orderLines.getInventoryItemId()).getItemCode();
                String itemDescription=itemsMapper.myQueryById(orderLines.getInventoryItemId()).getItemDescription();

                orderLines.setSumPrice(orderLines.getUnitSellingPrice()*orderLines.getOrderdQuantity());
                orderLines.setItemCode(itemCode);
                orderLines.setItemDescription(itemDescription);
            }
        }
        return orderLinesList;
    }
}