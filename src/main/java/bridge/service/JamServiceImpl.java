package bridge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bridge.dto.ConcertDto;
import bridge.dto.ConcertMusicDto;
import bridge.mapper.JamMapper;

@Service
public class JamServiceImpl implements JamService {
	@Autowired
	JamMapper jamMapper;

	@Override
	public int insertJam(ConcertDto concertDto) {
		// TODO Auto-generated method stub
		return jamMapper.insertJam(concertDto);
	}

	@Override
	public int insertMusic(ConcertMusicDto concertMusicDto) {
		// TODO Auto-generated method stub
		return jamMapper.inserMusic(concertMusicDto);
	}
}
