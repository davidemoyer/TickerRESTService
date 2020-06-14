package com.demoyer.tickerservice.response_builder;

import com.demoyer.tickerservice.delegate.TickerServiceDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

//wraps response in a response entity and returns, could read operationStatus in future and set custom httpStatus codes if error occurred
@Component
public class TickerServiceResponseBuilder {

    private final TickerServiceDelegate tickerServiceDelegate;

    @Autowired
    public TickerServiceResponseBuilder(TickerServiceDelegate tickerServiceDelegate) {
        this.tickerServiceDelegate = tickerServiceDelegate;
    }

    public ResponseEntity<?> getTickerInfoJSON(List<String> tickerList) {
        return new ResponseEntity<>(tickerServiceDelegate.getTickerDataJSON(tickerList), HttpStatus.OK);
    }

    public ResponseEntity<?> getTickerInfoCSV(List<String> tickerList) {
        return new ResponseEntity<>(tickerServiceDelegate.getTickerDataCSV(tickerList), HttpStatus.OK);
    }
}
