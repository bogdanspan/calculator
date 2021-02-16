package org.nexttech.interview.calculator.model.dto;

public class ComputationResponse {
    private ComputationRequest request;
    private ValidationInfo validationInfo;
    private Double result;

    public ComputationResponse(ComputationRequest request, ValidationInfo validationInfo, Double result) {
        this.request = request;
        this.validationInfo = validationInfo;
        this.result = result;
    }

    public ComputationRequest getRequest() {
        return request;
    }

    public void setRequest(ComputationRequest request) {
        this.request = request;
    }

    public ValidationInfo getValidationInfo() {
        return validationInfo;
    }

    public void setValidationInfo(ValidationInfo validationInfo) {
        this.validationInfo = validationInfo;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }
}
