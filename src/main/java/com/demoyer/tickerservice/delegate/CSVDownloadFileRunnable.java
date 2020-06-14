package com.demoyer.tickerservice.delegate;

import com.demoyer.tickerservice.model.TickerDataProviderResponse;
import com.demoyer.tickerservice.response_builder.HTTPOutputResponseBuilder;

import javax.servlet.http.HttpServletResponse;

public class CSVDownloadFileRunnable implements Runnable {

    TickerDataProviderResponse tickerDataProviderResponse;
    HttpServletResponse httpServletResponse;

    CSVDownloadFileRunnable(TickerDataProviderResponse tickerDataProviderResponse,
                            HttpServletResponse httpServletResponse) {
        this.tickerDataProviderResponse = tickerDataProviderResponse;
        this.httpServletResponse = httpServletResponse;
    }

    @Override
    public void run() {
        try {
            HTTPOutputResponseBuilder httpOutputResponseBuilder = new HTTPOutputResponseBuilder();
            httpOutputResponseBuilder.createCSVStringFileOutputStream(tickerDataProviderResponse, httpServletResponse);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
