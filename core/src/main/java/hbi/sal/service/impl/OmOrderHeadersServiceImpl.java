package hbi.sal.service.impl;

import com.hand.hap.code.rule.exception.CodeRuleException;
import com.hand.hap.code.rule.service.ISysCodeRuleProcessService;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hbi.demo.dto.DemoBank;
import hbi.demo.dto.DemoBankBranch;
import hbi.sal.dto.OmOrderLines;
import hbi.sal.mapper.OmOrderHeadersMapper;
import hbi.sal.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hbi.sal.dto.OmOrderHeaders;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class OmOrderHeadersServiceImpl extends BaseServiceImpl<OmOrderHeaders> implements IOmOrderHeadersService {
    @Autowired
    private IOmOrderHeadersService orderHeadersService;
    @Autowired
    private IOmOrderLinesService orderLinesService;
    @Autowired
    private IOrgCompanysService companysService;
    @Autowired
    private IArCustomersService customersService;
    @Autowired
    private IInvInventoryItemsService itemsService;
    @Autowired
    ISysCodeRuleProcessService codeRuleProcessService;
    @Autowired
    private OmOrderHeadersMapper orderHeadersMapper;

    @Override
    public List<OmOrderHeaders> myBatchSubmit(IRequest request, List<OmOrderHeaders> list) {
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                OmOrderHeaders orderHeaders = list.get(i);
                //根据是否拥bankId有判断是insert还是update
                Long headerId = orderHeaders.getHeaderId();
                if (headerId == null || headerId == 0) {
                    //保存订单编号和headerId
                    try {
                        headerId=Long.parseLong(codeRuleProcessService.getRuleCode("headerid"));
                        orderHeaders.setHeaderId(headerId);
                        orderHeaders.setOrderNumber(codeRuleProcessService.getRuleCode("sale"));
                    } catch (CodeRuleException e) {
                        e.printStackTrace();
                    }
                    //保存头
                    orderHeadersMapper.myInsert(orderHeaders);
                    //insert之后便有主键了
                    //保存行
                    List<OmOrderLines> orderLinesList = orderHeaders.getOrderLinesList();
                    if (orderLinesList != null && !orderLinesList.isEmpty()){
                        for (int j = 0; j < orderLinesList.size(); j++) {
                            OmOrderLines orderLines = orderLinesList.get(j);
                            orderLines.setHeaderId(headerId);
                            orderLines.setCompanyId(orderHeaders.getCompanyId());
                            orderLines.setInventoryItemId(itemsService.myQuery(orderLines.getItemCode()).getInventoryItemId());
                            //行表id
                            try {
                                orderLines.setLineId(Long.parseLong(codeRuleProcessService.getRuleCode("lineid")));
                            } catch (CodeRuleException e) {
                                e.printStackTrace();
                            }
                            orderLinesService.myInsert(orderLines);
                        }
                    }
                }
                else {
                    //update
                    //保存头
                    orderHeadersService.updateByPrimaryKeySelective(request, orderHeaders);
                    //保存行的时候需要区分行是新建还是更新
                    List<OmOrderLines> orderLinesList = orderHeaders.getOrderLinesList();
                    if (orderLinesList != null && !orderLinesList.isEmpty()) {
                        for (int j = 0; j < orderLinesList.size(); j++) {
                            OmOrderLines orderLines= orderLinesList.get(j);
                            Long lineId= orderLines.getLineId();
                            if (lineId == null) {
                                orderLines.setHeaderId(headerId);
                                orderLines.setCompanyId(orderHeaders.getCompanyId());
                                orderLines.setInventoryItemId(itemsService.myQuery(orderLines.getItemCode()).getInventoryItemId());

                                try {
                                    orderLines.setLineId(Long.parseLong(codeRuleProcessService.getRuleCode("lineid")));
                                } catch (CodeRuleException e) {
                                    e.printStackTrace();
                                }
                                orderLinesService.myInsert(orderLines);
                            } else {
                                orderLinesService.updateByPrimaryKeySelective(request,orderLines);
                            }
                        }
                    }
                }
            }
            return list;
        }
        return null;
    }

    /*
    * 查询
    * */
    @Override
    public List<OmOrderHeaders> myQuery(Long inventoryItemId, IRequest request, OmOrderHeaders dto, int page, int pageSize) {
        List<OmOrderHeaders> omOrderHeadersList=new ArrayList<OmOrderHeaders>();
        //查询物料条件不为空
        if (inventoryItemId != null) {
            //找出所有行
            OmOrderLines orderLinesSelect = new OmOrderLines();
            orderLinesSelect.setInventoryItemId(inventoryItemId);
            List<OmOrderLines> orderLinesList = orderLinesService.select(request, orderLinesSelect, 1, 0);
            if (orderLinesList!=null||orderLinesList.size()!=0){
                //找出所有头
                for (int i = 0; i <orderLinesList.size() ; i++) {
                    Long headerId=orderLinesList.get(i).getHeaderId();
                    OmOrderHeaders orderHeaders=orderHeadersMapper.myQueryById(headerId);

                    //查询公司的名称
                    String companyName = companysService.myQuery(orderHeaders.getCompanyId()).getCompanyName();
                    orderHeaders.setCompanyName(companyName);
                    //查询顾客的名称
                    String customerName = customersService.myQuery(orderHeaders.getCustomerId()).getCustomerName();
                    orderHeaders.setCustomerName(customerName);
                    //查询总金额
                    Long sumPrice = Long.valueOf(0);
                    OmOrderLines orderLinesSelect2 = new OmOrderLines();
                    orderLinesSelect.setHeaderId(headerId);
                    List<OmOrderLines> orderLinesList2 = orderLinesService.select(request, orderLinesSelect2, 1, 0);
                    if (orderLinesList2 != null || !orderLinesList2.isEmpty()) {
                        for (int j = 0; j < orderLinesList2.size(); j++) {
                            OmOrderLines orderLines = orderLinesList2.get(j);
                            sumPrice += orderLines.getOrderdQuantity() * orderLines.getUnitSellingPrice();
                        }
                    }
                    orderHeaders.setSumPrice(sumPrice);

                    //添加头
                    omOrderHeadersList.add(orderHeaders);
                }
            }
        }
        //没有物料查询
        else {
            List<OmOrderHeaders> orderHeadersList = orderHeadersService.select(request, dto, page, pageSize);
            if (orderHeadersList != null || orderHeadersList.size() != 0) {
                for (int i = 0; i < orderHeadersList.size(); i++) {
                    OmOrderHeaders orderHeaders = orderHeadersList.get(i);
                    //查询公司的名称
                    String companyName = companysService.myQuery(orderHeaders.getCompanyId()).getCompanyName();
                    orderHeaders.setCompanyName(companyName);
                    //查询顾客的名称
                    String customerName = customersService.myQuery(orderHeaders.getCustomerId()).getCustomerName();
                    orderHeaders.setCustomerName(customerName);
                    //查询总金额
                    Long sumPrice = Long.valueOf(0);
                    Long headerid = orderHeaders.getHeaderId();
                    OmOrderLines orderLinesSelect = new OmOrderLines();
                    orderLinesSelect.setHeaderId(headerid);
                    List<OmOrderLines> orderLinesList = orderLinesService.select(request, orderLinesSelect, 1, 0);
                    if (orderLinesList != null || !orderLinesList.isEmpty()) {
                        for (int j = 0; j < orderLinesList.size(); j++) {
                            OmOrderLines orderLines = orderLinesList.get(j);
                            sumPrice += orderLines.getOrderdQuantity() * orderLines.getUnitSellingPrice();
                        }
                    }
                    orderHeaders.setSumPrice(sumPrice);
                }
            }
            omOrderHeadersList=orderHeadersList;
        }
        return omOrderHeadersList;
    }

    @Override
    public void myDelete(IRequest request, List<OmOrderHeaders> list) throws Exception {
        if (list==null||list.isEmpty()){
            throw new Exception("缺失数据");
        }
        for (int i = 0; i < list.size(); i++) {
            OmOrderHeaders orderHeaders=list.get(i);
            Long headerid=orderHeaders.getHeaderId();

            OmOrderLines orderLinesSelect=new OmOrderLines();
            orderLinesSelect.setHeaderId(headerid);
            List<OmOrderLines> orderLinesList = orderLinesService.select(request, orderLinesSelect, 1, 0);
            if (orderLinesList!=null||!orderLinesList.isEmpty()){
                orderLinesService.batchDelete(orderLinesList);
            }
            orderHeadersService.deleteByPrimaryKey(orderHeaders);
        }
    }
}