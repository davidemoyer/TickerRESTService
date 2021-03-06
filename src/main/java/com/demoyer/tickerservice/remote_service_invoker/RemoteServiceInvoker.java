package com.demoyer.tickerservice.remote_service_invoker;

import com.demoyer.tickerservice.model.TickerData;
import com.demoyer.tickerservice.model.TickerDataProviderResponse;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

public class RemoteServiceInvoker {

    protected TickerDataProviderResponse getTickerDataProviderResponse(URI uri) {
        TickerDataProviderResponse tickerDataProviderResponse = new TickerDataProviderResponse();
        TickerData tickerData;
        RestTemplate restTemplate = new RestTemplate();

        try {
            tickerData = restTemplate.getForEntity(uri, TickerData.class).getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            return createErrorResponse();
        }
        tickerDataProviderResponse.setData(tickerData);

        return tickerDataProviderResponse;
    }

    protected TickerDataProviderResponse createErrorResponse() {
        TickerDataProviderResponse tickerDataProviderResponse = new TickerDataProviderResponse();
        tickerDataProviderResponse.getOperationStatus().setStatusCode("1");
        tickerDataProviderResponse.getOperationStatus().setStatusMessage("Bad http response from exchange");
        tickerDataProviderResponse.setData(null);
        return tickerDataProviderResponse;
    }
}
