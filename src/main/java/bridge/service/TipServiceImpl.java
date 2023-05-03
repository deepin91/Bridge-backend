package bridge.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bridge.dto.TipCommentsDto;
import bridge.dto.TipDto;
import bridge.mapper.TipMapper;

@Service
public class TipServiceImpl implements TipService{
	@Autowired
	TipMapper tipMapper;
	
	@Override
	public int insertTip(TipDto tipDto) {
		// TODO Auto-generated method stub
		return tipMapper.insertTip(tipDto);
	}

	@Override
	public TipDto tipdetail(int tbIdx) {
		// TODO Auto-generated method stub
		return tipMapper.tipdetail(tbIdx);
	}

	@Override
	public List<TipCommentsDto> tipcommentslist(int tbIdx) {
		// TODO Auto-generated method stub
		return tipMapper.tipcommentslist(tbIdx);
	}

	@Override
	public List<TipDto> tipList() {
		// TODO Auto-generated method stub
		return tipMapper.tipList();
	}

	@Override
	public void updateViews(int tbIdx) {
		// TODO Auto-generated method stub
		tipMapper.updateViews(tbIdx);
	}

	@Override
	public void insertComment(TipCommentsDto tipCommentsDto) {
		// TODO Auto-generated method stub
		tipMapper.insertComment(tipCommentsDto);
		
	}

	@Override
	public void updateTip(TipDto tipDto) {
		// TODO Auto-generated method stub
		tipMapper.updateTip(tipDto);
	}

}
