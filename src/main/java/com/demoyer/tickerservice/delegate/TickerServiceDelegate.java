package com.demoyer.tickerservice.delegate;

import com.demoyer.tickerservice.client.TickerServiceClient;
import com.demoyer.tickerservice.model.Response;
import com.demoyer.tickerservice.model.TickerDataProviderResponse;
import com.demoyer.tickerservice.response_builder.HTTPOutputResponseBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.demoyer.tickerservice.common.Constant.*;

//performs any operations on data
@Component
public class TickerServiceDelegate {

    private final TickerServiceClient tickerServiceClient;

    @Autowired
    public TickerServiceDelegate(TickerServiceClient tickerServiceClient) {
        this.tickerServiceClient = tickerServiceClient;
    }

    public List<TickerDataProviderResponse> getTickerDataJSON(List<String> tickerList) {
        return tickerServiceClient.getTickerData(tickerList);
    }

    public Response<?> getTickerDataCSV(List<String> tickerList) {
        List<TickerDataProviderResponse> tickerDataProviderResponseList = tickerServiceClient.getTickerData(tickerList);
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        tickerDataProviderResponseList.forEach((tickerDataProviderResponse) -> {
            if (!tickerDataProviderResponse
                    .getData()
                    .getTickerInfoMap()
                    .isEmpty()) {
                executorService.execute(new CSVLocalFileRunnable(tickerDataProviderResponse));
            }
        });

        if (!shutdownExecutorService(executorService)) {
            return createErrorResponse();
        }
        return new Response<>();
    }

    public void downloadTickerDataCSV(String ticker, HttpServletResponse httpServletResponse) {
        TickerDataProviderResponse tickerDataProviderResponse = tickerServiceClient.getTickerData(ticker);

        HTTPOutputResponseBuilder httpOutputResponseBuilder = new HTTPOutputResponseBuilder();

        try {
            if (tickerDataProviderResponse
                    .getOperationStatus()
                    .getStatusCode()
                    .equalsIgnoreCase(OPERATION_STATUS_SUCCESSFUL_CODE)) {
                httpOutputResponseBuilder.createCSVStringFileOutputStream(tickerDataProviderResponse, httpServletResponse);
            } else {
                printObject(tickerDataProviderResponse);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private Response<?> createErrorResponse() {
        Response<?> response = new Response<>();

        response.getOperationStatus()
                .setStatusCode(OPERATION_STATUS_FAILURE_CODE);

        response.getOperationStatus()
                .setStatusMessage(OPERATION_STATUS_EXECUTOR_ERROR_CODE);
        return response;
    }

    private boolean shutdownExecutorService(ExecutorService executorService) {
        executorService.shutdown();
        try {
            executorService.awaitTermination(20, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println(e.getLocalizedMessage());
        }
        return executorService.isShutdown();
    }

    private void printObject(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException jsonProcessingException) {
            jsonProcessingException.printStackTrace();
        }
    }
}
