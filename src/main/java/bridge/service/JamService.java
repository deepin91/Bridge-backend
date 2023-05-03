package bridge.service;

import bridge.dto.ConcertDto;
import bridge.dto.ConcertMusicDto;

public interface JamService {

	int insertJam(ConcertDto concertDto);

	int insertMusic(ConcertMusicDto concertMusicDto);

}
