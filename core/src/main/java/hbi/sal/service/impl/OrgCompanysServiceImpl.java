package hbi.sal.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import hbi.sal.mapper.OrgCompanysMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hbi.sal.dto.OrgCompanys;
import hbi.sal.service.IOrgCompanysService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrgCompanysServiceImpl extends BaseServiceImpl<OrgCompanys> implements IOrgCompanysService{

    @Autowired
    private OrgCompanysMapper companysMapper;
    @Override
    public OrgCompanys myQuery(Long companyId) {
        OrgCompanys companys=companysMapper.myQuery(companyId);
        return companys;
    }
}