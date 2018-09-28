package hbi.sal.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hbi.sal.dto.InvInventoryItems;

import java.util.List;

public interface InvInventoryItemsMapper extends Mapper<InvInventoryItems>{
   public List<InvInventoryItems> myselect();
   public InvInventoryItems myQuery(String itemCode);
   public InvInventoryItems myQueryById(Long inventoryItemId);
}