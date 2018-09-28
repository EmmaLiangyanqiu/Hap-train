package hbi.sal.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hbi.sal.dto.InvInventoryItems;

public interface IInvInventoryItemsService extends IBaseService<InvInventoryItems>, ProxySelf<IInvInventoryItemsService>{
  public InvInventoryItems myQuery(String itemCode);
}