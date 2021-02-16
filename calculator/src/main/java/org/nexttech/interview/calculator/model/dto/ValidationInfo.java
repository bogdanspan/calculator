package org.nexttech.interview.calculator.model.dto;

import java.util.List;

public class ValidationInfo {
    private boolean valid;
    private List<String> errorMessages;

    public ValidationInfo(boolean valid, List<String> errorMessages) {
        this.valid = valid;
        this.errorMessages = errorMessages;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }
}
