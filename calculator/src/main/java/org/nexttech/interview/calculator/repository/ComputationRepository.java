package org.nexttech.interview.calculator.repository;

import org.nexttech.interview.calculator.model.dao.Computation;
import org.springframework.data.repository.CrudRepository;

public interface ComputationRepository extends CrudRepository<Computation, String> {
}
