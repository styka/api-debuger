package pl.aia.wallet.service;

import java.util.Collection;
import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pl.aia.wallet.model.Operation;
import pl.aia.wallet.repository.OperationRepository;
import pl.aia.wallet.utils.BigDecimalUtil; 

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class OperationServiceBean implements OperationService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private OperationRepository operationRepository;

	@Override
	public Collection<Operation> findAll() {
		Collection<Operation> operations = operationRepository.findAll();
		return operations;
	}

	@Override
	@Cacheable(value="operations",key="#id")
	public Operation findOne(Long id) {
		logger.info("START findOne(" + id +")");
		Operation operation = operationRepository.getOne(id);
		if (operation == null) {
			return null;
		}
		Operation responseOperation = new Operation();
		BeanUtils.copyProperties(operation, responseOperation);
		return operation;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@CachePut(value="operations",key="#result.id")
	public Operation create(Operation operation) {
		Long id = operation.getId();
		if (id != null) {
			return null;
		}
		operation.setDate(new Date());
		operation = operationRepository.save(operation);
		Operation responseOperation = new Operation();
		BeanUtils.copyProperties(operation, responseOperation);
		return operation;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@CachePut(value="operations",key="#operation.id")
	public Operation update(Operation operation) {
		Long id = operation.getId();
		if (id == null) {
			return null;
		}
		Operation oldOperation = operationRepository.getOne(id);
		if (oldOperation == null) {
			return null;
		}
		operation = operationRepository.save(operation);
		return operation;

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@CacheEvict(value="operations",key="#id")
	public void delete(Long id) {
		operationRepository.delete(id);
	}

	@Override
	public Operation generate() {
		Operation operation = new Operation();
		operation.setAmount(BigDecimalUtil.random(1000));
		operation.setTitle("Rata nr " + (new Random().nextInt(100)) + "/" + 100);
		return operation;
	}
	
	@Override
	@CacheEvict(value="operations",allEntries = true)
	public void evictCache(){
		
	}

}
