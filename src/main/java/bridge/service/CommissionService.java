package bridge.service;

import java.util.List;

import bridge.dto.CommissionDetailDto;
import bridge.dto.CommissionDto;

public interface CommissionService {

	List<CommissionDto> getCommissionList(String userId);

	List<CommissionDto> getCommissionDetail(int cIdx);

	int insertCommissionDetail(CommissionDetailDto commissionDetail);

	void editCommissionDetail(CommissionDetailDto commissionDetail);

	void delCommissionDetail(int cdIdx);

	void delCommissionFile(int cdIdx);

	List<CommissionDto> getProgress(int cIdx);

	void commissionEnd(int cIdx);

	void insertCommission(CommissionDto commissionDto);

	void delCommissionList(int cIdx);

	void moneyToUser2(int cIdx);

}
