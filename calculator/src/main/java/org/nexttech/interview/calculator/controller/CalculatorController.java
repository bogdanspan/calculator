package org.nexttech.interview.calculator.controller;

import org.nexttech.interview.calculator.model.dto.ComputationRequest;
import org.nexttech.interview.calculator.model.dto.ComputationResponse;
import org.nexttech.interview.calculator.service.CalculatorDispatcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/operation/")
public class CalculatorController {

    @Autowired
    private CalculatorDispatcherService calculatorDispatcherService;

    @RequestMapping(method = RequestMethod.GET, path = "/{op}/{num1}/{num2}")
    public ResponseEntity<ComputationResponse> compute(@PathVariable("op") String operation, @PathVariable("num1") String firstTerm, @PathVariable("num2") String secondTerm) {
        ComputationResponse response = this.calculatorDispatcherService.compute(new ComputationRequest(operation, firstTerm, secondTerm));
        return response.getValidationInfo().isValid() ? ResponseEntity.ok(response) : ResponseEntity.badRequest().body(response);
    }

}
