package pl.aia.wallet.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import pl.aia.wallet.model.Operation;
import pl.aia.wallet.service.OperationService;

@Component
@Profile("batch")
public class OperationBatchBean {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private OperationService operationService;
	
	@Scheduled(cron = "${operationBatch.cron}")
	public void addOperationEvery20Secounds(){
		logger.info("START addOperationEvery20Secounds()");
		Operation operation = operationService.generate();
		operation = operationService.create(operation);
		logger.info("STOP addOperationEvery20Secounds: [" + operation.id +"] " + operation);
		
	}
}
