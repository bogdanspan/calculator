package org.nexttech.interview.calculator.model.dao;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "COMPUTATION")
public class Computation {

    @Id
    private String expressionId;

    private double result;

    public Computation() {
    }

    public Computation(String expressionId, double result) {
        this.expressionId = expressionId;
        this.result = result;
    }

    public String getExpressionId() {
        return expressionId;
    }

    public void setExpressionId(String expressionId) {
        this.expressionId = expressionId;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }
}
