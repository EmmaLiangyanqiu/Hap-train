package hbi.sal.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hbi.sal.dto.OrgCompanys;

import java.util.List;

public interface IOrgCompanysService extends IBaseService<OrgCompanys>, ProxySelf<IOrgCompanysService>{
  /*
  * 查询公司用id
  * */
  public OrgCompanys myQuery(Long companyId);
}