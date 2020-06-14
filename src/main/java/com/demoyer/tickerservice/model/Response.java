package com.demoyer.tickerservice.model;

public class Response<T> {

    private OperationStatus operationStatus = new OperationStatus();
    private T data;

    public OperationStatus getOperationStatus() {
        return operationStatus;
    }

    public void setOperationStatus(OperationStatus operationStatus) {
        this.operationStatus = operationStatus;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
