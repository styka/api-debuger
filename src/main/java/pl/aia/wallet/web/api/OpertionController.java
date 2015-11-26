package pl.aia.wallet.web.api;

import java.util.Collection;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.aia.wallet.model.Operation;
import pl.aia.wallet.service.OperationService;

@RestController
@RequestMapping(value = "/operations")
public class OpertionController {

	@Autowired
	private OperationService operationService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Collection<Operation>> getOpertions() {
		final Collection<Operation> operations = operationService.findAll();
		return new ResponseEntity<Collection<Operation>>(operations, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Operation> getOne(@PathVariable("id") final Long id) {
		final Operation operation = operationService.findOne(id);
		if (operation == null)
			return new ResponseEntity<Operation>(HttpStatus.NOT_FOUND);
		final Operation responseOperation = new Operation();
		BeanUtils.copyProperties(operation, responseOperation);
		return new ResponseEntity<Operation>(responseOperation, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Operation> createOperation(@RequestBody final Operation operation) {
		final Operation saveOperation = operationService.create(operation);
		if (saveOperation == null) {
			return new ResponseEntity<Operation>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		final Operation responseOperation = new Operation();
		BeanUtils.copyProperties(operation, responseOperation);
		return new ResponseEntity<Operation>(responseOperation, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Operation> updateOperation(@PathVariable("id") final Long id,
			@RequestBody final Operation operation) {
		final Operation updateOperation = operationService.update(operation);
		if (updateOperation == null) {
			return new ResponseEntity<Operation>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		final Operation responseOperation = new Operation();
		BeanUtils.copyProperties(operation, responseOperation);
		return new ResponseEntity<Operation>(responseOperation, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Operation> deleteOperation(@PathVariable("id") final Long id) {
		operationService.delete(id);
		return new ResponseEntity<Operation>(HttpStatus.OK);
	}

	@RequestMapping(value = "/generate", method = RequestMethod.GET)
	public ResponseEntity<Operation> getFutureOpertion() {
		final Operation generateOpertion = operationService.generate();
		return new ResponseEntity<Operation>(generateOpertion, HttpStatus.OK);
	}

}
