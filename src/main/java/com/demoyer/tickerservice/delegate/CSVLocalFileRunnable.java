package com.demoyer.tickerservice.delegate;

import com.demoyer.tickerservice.model.TickerDataProviderResponse;
import com.demoyer.tickerservice.response_builder.HTTPOutputResponseBuilder;

public class CSVLocalFileRunnable implements Runnable {

    TickerDataProviderResponse tickerDataProviderResponse;

    CSVLocalFileRunnable(TickerDataProviderResponse tickerDataProviderResponse) {
        this.tickerDataProviderResponse = tickerDataProviderResponse;
    }

    @Override
    public void run() {
        try {
            HTTPOutputResponseBuilder httpOutputResponseBuilder = new HTTPOutputResponseBuilder();
            httpOutputResponseBuilder.createCSVWriterOutputStream(tickerDataProviderResponse);
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }
}
