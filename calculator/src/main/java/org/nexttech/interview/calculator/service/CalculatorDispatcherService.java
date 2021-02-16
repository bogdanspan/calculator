package org.nexttech.interview.calculator.service;

import org.nexttech.interview.calculator.model.dao.Computation;
import org.nexttech.interview.calculator.model.dto.ComputationRequest;
import org.nexttech.interview.calculator.model.dto.ComputationResponse;
import org.nexttech.interview.calculator.model.dto.OperationType;
import org.nexttech.interview.calculator.model.dto.ValidationInfo;
import org.nexttech.interview.calculator.repository.ComputationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CalculatorDispatcherService {

    private static final Logger logger = LoggerFactory.getLogger(CalculatorDispatcherService.class);

    @Autowired
    private CalculatorService calculatorService;

    @Autowired
    private ComputationRepository computationRepository;

    @Autowired
    private CacheManager cacheManager;

    @Cacheable(value = "computations-cache", key = "#computationRequest.firstTerm + #computationRequest.operationType + #computationRequest.secondTerm")
    public ComputationResponse compute(ComputationRequest computationRequest) {

        ValidationInfo validationInfo = this.calculatorService.validateRequest(computationRequest);

        if (!validationInfo.isValid()) {
            logger.error(String.format("Error for the request: {firstTerm: %s, secondTerm: %s, operationType: %s} : %s", computationRequest.getFirstTerm(), computationRequest.getSecondTerm(), computationRequest.getOperationType(), String.join(", ", validationInfo.getErrorMessages())));
            return new ComputationResponse(computationRequest, validationInfo, null);
        } else {
            String idComputation = this.calculatorService.createComputationIdentifier(computationRequest);
            Optional<Computation> result = this.computationRepository.findById(idComputation);

            if (result.isPresent()) {
                logger.info(String.format("Result for the request: {firstTerm: %s, secondTerm: %s, operationType: %s} was obtained obtained from -> DB with defined order of parameters", computationRequest.getFirstTerm(), computationRequest.getSecondTerm(), computationRequest.getOperationType()));
                return new ComputationResponse(computationRequest, new ValidationInfo(true, List.of()), result.get().getResult());
            } else if (List.of(OperationType.ADDITION, OperationType.MULTIPLICATION).contains(OperationType.valueOf(computationRequest.getOperationType().toUpperCase()))) {
                result = this.computationRepository.findById(this.calculatorService.createComputationIdentifier(new ComputationRequest(computationRequest.getOperationType(), computationRequest.getSecondTerm(), computationRequest.getFirstTerm())));
                if (result.isPresent()) {
                    this.cacheManager.getCache("computation-cache").put("" + computationRequest.getSecondTerm() + computationRequest.getOperationType() + computationRequest.getFirstTerm(), null);
                    logger.info(String.format("Result for the request: {firstTerm: %s, secondTerm: %s, operationType: %s} was obtained obtained from -> DB with reversed order of parameters", computationRequest.getFirstTerm(), computationRequest.getSecondTerm(), computationRequest.getOperationType()));
                    return new ComputationResponse(computationRequest, new ValidationInfo(true, List.of()), result.get().getResult());
                }

            }

            ComputationResponse computationResponse = this.calculatorService.compute(computationRequest);
            logger.info(String.format("Result for the request: {firstTerm: %s, secondTerm: %s, operationType: %s} was obtained obtained from -> CalculatorService", computationRequest.getFirstTerm(), computationRequest.getSecondTerm(), computationRequest.getOperationType()));

            computationRepository.save(new Computation(idComputation, computationResponse.getResult()));
            return computationResponse;
        }

    }

}
