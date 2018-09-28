package hbi.demo.service.impl;

import com.hand.hap.code.rule.exception.CodeRuleException;
import com.hand.hap.code.rule.service.ISysCodeRuleProcessService;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hbi.demo.dto.DemoBankBranch;
import hbi.demo.service.IDemoBankBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hbi.demo.dto.DemoBank;
import hbi.demo.service.IDemoBankService;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class DemoBankServiceImpl extends BaseServiceImpl<DemoBank> implements IDemoBankService{
     @Autowired
     private IDemoBankService demoBankService;
     @Autowired
     private IDemoBankBranchService demoBankBranchService;

    @Autowired
    ISysCodeRuleProcessService codeRuleProcessService;

    @Override
    public int myBatchDelete(IRequest request, List<DemoBank> list) throws Exception {
        int count=0;
        if (list==null||list.isEmpty()){
            throw new Exception("缺失数据");
        }
        for (int i = 0; i < list.size(); i++) {
            DemoBank demoBank=list.get(i);
            long bankId=demoBank.getBankId();

            DemoBankBranch demoBankBranchSelect=new DemoBankBranch();
            demoBankBranchSelect.setBankId(bankId);
            List<DemoBankBranch> demoBankBranchList = demoBankBranchService.select(request, demoBankBranchSelect, 1, 0);
            if (demoBankBranchList!=null||!demoBankBranchList.isEmpty()){
                //删除行结构
                demoBankBranchService.batchDelete(demoBankBranchList);
            }
            //删除头结构
            int n = demoBankService.deleteByPrimaryKey(demoBank);
            count+=n;
        }
        return count;
    }

    @Override
    public List<DemoBank> myBatchSubmit(IRequest request, List<DemoBank> list) {
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                DemoBank demoBank = list.get(i);
                //根据是否拥bankId有判断是insert还是update
                Long bankId = demoBank.getBankId();
                if (bankId == null||bankId==0) {
                    //insert
                    //保存头
                    demoBankService.insertSelective(request, demoBank);
                    //insert之后便有主键了
                    bankId = demoBank.getBankId();
                    //保存行
                    List<DemoBankBranch> demoBankBranchList = demoBank.getDemoBankBranchList();
                    if (demoBankBranchList != null && !demoBankBranchList.isEmpty()){
                        for (int j = 0; j < demoBankBranchList.size(); j++) {
                            DemoBankBranch demoBankBranch = demoBankBranchList.get(j);
                            demoBankBranch.setBankId(bankId);
                            demoBankBranchService.insertSelective(request, demoBankBranch);
                        }
                    }
                } else {
                    //update
                    //保存头
                    demoBankService.updateByPrimaryKeySelective(request, demoBank);
                    //保存行的时候需要区分行是新建还是更新
                    List<DemoBankBranch> demoBankBranchList = demoBank.getDemoBankBranchList();
                    if (demoBankBranchList != null && !demoBankBranchList.isEmpty()) {
                        for (int j = 0; j < demoBankBranchList.size(); j++) {
                            DemoBankBranch demoBankBranch = demoBankBranchList.get(j);
                            Long bankBranchId = demoBankBranch.getBankBranchId();
                            if (bankBranchId == null) {
                                demoBankBranch.setBankId(bankId);
                                demoBankBranchService.insertSelective(request, demoBankBranch);
                            } else {
                                demoBankBranchService.updateByPrimaryKeySelective(request, demoBankBranch);
                            }
                        }
                    }
                }
            }
            return list;
        }
        return null;
    }

    @Override
    public void test(){
        //使用动态参数，需要传入一个Map
        //比如编码定义了一个变量${var}
        Map<String,String> map = new HashMap<>();
        map.put("bankname","bank");
        try {
            String str=codeRuleProcessService.getRuleCode("demo",map);
            System.out.println(str);
        } catch (CodeRuleException e) {
            e.printStackTrace();
        }
    }
}