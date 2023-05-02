package bridge.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import bridge.dto.UserDto;
import bridge.entity.ChattingEntity;
import bridge.entity.MessageEntity;
import bridge.service.JpaService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class JpaMessageController {

    private final SimpMessageSendingOperations simpMessageSendingOperations;
    
    @Autowired
    private JpaService jpaService;
    
    @GetMapping("/chatroom")
    public ResponseEntity<Map<String,Object>> chatroom(Authentication authentication){
    	UserDto userDto = (UserDto) authentication.getPrincipal();
    	List<ChattingEntity> chattingEntity = jpaService.getChattingRoom(userDto.getUserId());
    	Map<String,Object> map = new HashMap<>();
    	map.put("sender", userDto.getUserId());
    	map.put("chatting", chattingEntity);
    	return ResponseEntity.status(HttpStatus.OK).body(map);
    }
    
    
    @GetMapping("/chat/{roomIdx}")
	public ResponseEntity<Map<String, Object>> connect(@PathVariable("roomIdx") int roomIdx){
    	Map<String,Object> map = new HashMap<>();
    	List<MessageEntity> MessageEntity = jpaService.getMessage(roomIdx);
    	map.put("messagelist", MessageEntity);
    	ChattingEntity chattingEntity = jpaService.getchatting(roomIdx);
    	
    	map.put("chatting",chattingEntity);
    	return ResponseEntity.status(HttpStatus.OK).body(map);
    }
    @MessageMapping("/hello")
    public void message(MessageEntity message) {
    	simpMessageSendingOperations.convertAndSend("/sub/channel/" + message.getRoomIdx(), message);
    	jpaService.insertData(message);
    }
    // 1. 채팅방을 클릭하면 get 룸아이디엑스를 파라미터로 받는다
    // 2. 1번방(1 유저) 2번 방(2번 유저) 뽑아서 리턴시켜준다 클라이언트 쪽으로
    // 3. 룸 인데스를 가지고 메세지 내역에서 같은 것들을 클라이언트 쪽으로 리턴시켜준다
    // 4. 클라이언트가 그걸 받으면 그 데이터를 토대로 채널에 연결을 한다
    // 5. 데이터 넘어가면 알아서 db에 저장이되고 이거는 실시간 채팅은 클라이언트에서 구현할 거같아요 이게 내가 쓴 채팅 내역은
    // 리턴이 아마 안될거 같아서 클라이언트쪽에서 처리할 예정? 이건 방법을 찾으면 바뀔수도 있고 아닐 수도 있고
}