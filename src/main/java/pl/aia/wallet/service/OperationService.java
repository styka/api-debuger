package pl.aia.wallet.service;

import java.util.Collection;

import pl.aia.wallet.model.Operation;

public interface OperationService {

	Collection<Operation> findAll();

	Operation findOne(Long id);

	void delete(Long id);

	Operation generate();

	Operation update(Operation operation);

	Operation create(Operation operation);

	void evictCache();
}
