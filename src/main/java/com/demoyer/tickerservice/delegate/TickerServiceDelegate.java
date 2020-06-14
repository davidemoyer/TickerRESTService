package com.demoyer.tickerservice.delegate;

import com.demoyer.tickerservice.client.TickerServiceClient;
import com.demoyer.tickerservice.model.Response;
import com.demoyer.tickerservice.model.TickerDataProviderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.demoyer.tickerservice.common.Constant.OPERATION_STATUS_EXECUTOR_ERROR_CODE;
import static com.demoyer.tickerservice.common.Constant.OPERATION_STATUS_FAILURE_CODE;

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
            if (!tickerDataProviderResponse.getData().getTickerInfoMap().isEmpty())
                executorService.execute(new CSVLocalFileRunnable(tickerDataProviderResponse));
        });

        if (!shutdownExecutorService(executorService)) {
            return createErrorResponse();
        }
        return new Response<>();
    }

    public void downloadTickerDataCSV(List<String> tickerList, HttpServletResponse httpServletResponse) {
        List<TickerDataProviderResponse> tickerDataProviderResponseList = tickerServiceClient.getTickerData(tickerList);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new CSVDownloadFileRunnable(tickerDataProviderResponseList.get(0), httpServletResponse));
        shutdownExecutorService(executorService);
    }

    private Response<?> createErrorResponse() {
        Response<?> response = new Response<>();
        response.getOperationStatus().setStatusCode(OPERATION_STATUS_FAILURE_CODE);
        response.getOperationStatus().setStatusMessage(OPERATION_STATUS_EXECUTOR_ERROR_CODE);
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
}
