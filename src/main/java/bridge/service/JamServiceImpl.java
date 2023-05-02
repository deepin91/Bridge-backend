package bridge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bridge.mapper.JamMapper;

@Service
public class JamServiceImpl implements JamService {
	@Autowired
	JamMapper jamMapper;
}
