package bridge.mapper;

import org.apache.ibatis.annotations.Mapper;

import bridge.dto.ConcertDto;
import bridge.dto.ConcertMusicDto;

@Mapper
public interface JamMapper {

	int insertJam(ConcertDto concertDto);

	int inserMusic(ConcertMusicDto concertMusicDto);

}
