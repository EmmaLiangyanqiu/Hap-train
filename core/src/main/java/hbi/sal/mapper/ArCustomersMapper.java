package hbi.sal.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hbi.sal.dto.ArCustomers;

import java.util.List;

public interface ArCustomersMapper extends Mapper<ArCustomers>{
  public List<ArCustomers> myselect();
  public ArCustomers myQuery(Long customerId);
}