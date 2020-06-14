package com.demoyer.tickerservice.client;

import com.demoyer.tickerservice.model.RestServiceInfo;
import com.demoyer.tickerservice.model.TickerDataProviderResponse;
import com.demoyer.tickerservice.remote_service_invoker.TickerServiceRemoteServiceThreadRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Component
public class TickerServiceClient {

    final
    RestServiceInfo alphaVantageRestServiceInfo;

    private final TickerServiceRemoteServiceThreadRunner tickerServiceRemoteServiceThreadRunner;

    @Autowired
    public TickerServiceClient(TickerServiceRemoteServiceThreadRunner tickerServiceRemoteServiceThreadRunner,
                               @Qualifier("alphaVantageRSInfo") RestServiceInfo alphaVantageRestServiceInfo) {
        this.tickerServiceRemoteServiceThreadRunner = tickerServiceRemoteServiceThreadRunner;
        this.alphaVantageRestServiceInfo = alphaVantageRestServiceInfo;
    }

    public List<TickerDataProviderResponse> getTickerData(List<String> tickerList) {
        List<URI> tickerURIList = new ArrayList<>();

        for (String ticker : tickerList) {
            tickerURIList.add(createAPIURI(ticker));
        }

        return tickerServiceRemoteServiceThreadRunner.getTickerData(tickerURIList);
    }

    public TickerDataProviderResponse getTickerData(String ticker) {
        return tickerServiceRemoteServiceThreadRunner.getTickerData(createAPIURI(ticker));
    }

    private URI createAPIURI(String ticker) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(alphaVantageRestServiceInfo.getUrl() + ticker + "&interval=5min&apikey=" + alphaVantageRestServiceInfo.getApiKey());
        return uriComponentsBuilder.build().toUri();
    }
}
