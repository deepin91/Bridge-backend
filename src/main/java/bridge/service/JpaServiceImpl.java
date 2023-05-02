package bridge.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bridge.entity.ChattingEntity;
import bridge.entity.MessageEntity;
import bridge.repository.JpaChattingRepository;
import bridge.repository.JpaMessageRepository;

@Service
public class JpaServiceImpl implements JpaService {
	@Autowired
	private JpaMessageRepository jpaMessageRepository;
	@Autowired
	private JpaChattingRepository jpaChattingRepository;
	@Override
	public List<MessageEntity> getMessage(int roomIdx) {
		return (List<MessageEntity>) jpaMessageRepository.findByRoomIdx(roomIdx);
	}
	
	@Override
	public void insertData(MessageEntity messageEtity) {
		jpaMessageRepository.save(messageEtity);
	}

	@Override
	public ChattingEntity getchatting(int roomIdx) {
		Optional<ChattingEntity> optional = jpaChattingRepository.findById(roomIdx);
		ChattingEntity chatting = optional.get();
		return chatting;
	}

	@Override
	public List<ChattingEntity> getChattingRoom(String userId) {
		List<ChattingEntity> a = (List<ChattingEntity>) jpaChattingRepository.findByUserId1(userId);
		List<ChattingEntity> b = (List<ChattingEntity>) jpaChattingRepository.findByUserId2(userId);
		System.out.println("aaaaaaaaaaaaaaaaaaaaaa" + a+"BBBBBBBBBBBBBBBBBBBBBBBBBBB" + b);
		if(a != null) {
			return a;
		}else {
			return b;
		}
	}




}
