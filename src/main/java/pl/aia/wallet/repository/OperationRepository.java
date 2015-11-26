package pl.aia.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.aia.wallet.model.Operation;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long>{

}
