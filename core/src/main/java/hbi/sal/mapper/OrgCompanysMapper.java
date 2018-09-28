package hbi.sal.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hbi.sal.dto.OrgCompanys;

import java.util.List;

public interface OrgCompanysMapper extends Mapper<OrgCompanys>{
    public List<OrgCompanys> myselect();
    /*
    * 查询公司名称显示
    * */
    public OrgCompanys myQuery(Long companyId);

}