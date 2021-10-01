package br.com.buzz.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.buzz.domain.Log;
import br.com.buzz.repository.LogRepository;

@Service
public class LogService {
	
	@Autowired
	private LogRepository repository;
	
	private static final Logger LOG = LoggerFactory.getLogger(LogService.class);


	public Log insert(Log log) {
		log.setId(null);
		LOG.error(log.getErro());
		return repository.save(log);
	}

	public Page<Log> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}
}
