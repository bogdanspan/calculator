package org.nexttech.interview.calculator.service;

import org.nexttech.interview.calculator.model.dto.ComputationRequest;
import org.nexttech.interview.calculator.model.dto.ComputationResponse;
import org.nexttech.interview.calculator.model.dto.OperationType;
import org.nexttech.interview.calculator.model.dto.ValidationInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CalculatorService {

    public ComputationResponse compute(ComputationRequest computationRequest) {
        Double result = null;
        switch (OperationType.valueOf(computationRequest.getOperationType().toUpperCase())) {
            case ADDITION: {
                result = this.add(computationRequest);
                break;
            }
            case SUBSTRACTION: {
                result = this.substract(computationRequest);
                break;
            }
            case MULTIPLICATION: {
                result = this.multiply(computationRequest);
                break;
            }
            case DIVISION: {
                result = this.divide(computationRequest);
                break;
            }
            default: {
                System.err.println("Error: Unsupported operation !");
                break;
            }
        }
        return new ComputationResponse(computationRequest, new ValidationInfo(true, List.of()), result);
    }

    public ValidationInfo validateRequest(ComputationRequest request) {
        List<String> supportedOperations = List.of("addition", "substraction", "multiplication", "division");
        List<String> errorMessages = new ArrayList<>();
        String regexNumber = "^[+|-]?\\d+\\.?[\\d]*$";

        if (!supportedOperations.contains(request.getOperationType().toLowerCase())) {
            errorMessages.add("Unsupported operation !");
        }

        if (!request.getFirstTerm().matches(regexNumber)) {
            errorMessages.add("First term is not a number !");
        }

        if (!request.getSecondTerm().matches(regexNumber)) {
            errorMessages.add("Second term is not a number !");
        }

        if (request.getOperationType().equals(OperationType.DIVISION) && request.getSecondTerm().matches(regexNumber)
                && Double.parseDouble(request.getSecondTerm()) == 0) {
            errorMessages.add("Divided by 0 !");
        }

        return new ValidationInfo(errorMessages.size() <= 0, errorMessages);

    }

    public Double add(ComputationRequest request) {
        return Double.parseDouble(request.getFirstTerm()) + Double.parseDouble(request.getSecondTerm());
    }

    public Double substract(ComputationRequest request) {
        return Double.parseDouble(request.getFirstTerm()) - Double.parseDouble(request.getSecondTerm());
    }

    public Double multiply(ComputationRequest request) {
        return Double.parseDouble(request.getFirstTerm()) * Double.parseDouble(request.getSecondTerm());
    }

    public Double divide(ComputationRequest request) {
        return Double.parseDouble(request.getFirstTerm()) / Double.parseDouble(request.getSecondTerm());
    }

    public String createComputationIdentifier(ComputationRequest computationRequest) {
        Map<OperationType, String> operations = Map.of(OperationType.ADDITION, "+", OperationType.SUBSTRACTION, "-", OperationType.MULTIPLICATION, "*", OperationType.DIVISION, "/");
        return computationRequest.getFirstTerm() + operations.get(OperationType.valueOf(computationRequest.getOperationType().toUpperCase())) + computationRequest.getSecondTerm();
    }
}
