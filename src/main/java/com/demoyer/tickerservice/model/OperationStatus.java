package com.demoyer.tickerservice.model;

public class OperationStatus {

    String statusCode = "0";
    String statusMessage = "Operation was Successful";

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
