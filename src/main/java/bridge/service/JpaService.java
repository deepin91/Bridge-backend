package bridge.service;

import java.util.List;

import bridge.entity.ChattingEntity;
import bridge.entity.MessageEntity;

public interface JpaService {
	List<MessageEntity> getMessage(int roomIdx);
	void insertData(MessageEntity messageEtity);
	ChattingEntity getchatting(int roomIdx);
	List<ChattingEntity> getChattingRoom(String userId);

}
