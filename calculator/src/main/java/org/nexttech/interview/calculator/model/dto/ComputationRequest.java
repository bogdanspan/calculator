package org.nexttech.interview.calculator.model.dto;

public class ComputationRequest {
    private String operationType;
    private String firstTerm;
    private String secondTerm;


    public ComputationRequest(String operationType, String firstTerm, String secondTerm) {
        this.operationType = operationType;
        this.firstTerm = firstTerm;
        this.secondTerm = secondTerm;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getFirstTerm() {
        return firstTerm;
    }

    public void setFirstTerm(String firstTerm) {
        this.firstTerm = firstTerm;
    }

    public String getSecondTerm() {
        return secondTerm;
    }

    public void setSecondTerm(String secondTerm) {
        this.secondTerm = secondTerm;
    }
}
