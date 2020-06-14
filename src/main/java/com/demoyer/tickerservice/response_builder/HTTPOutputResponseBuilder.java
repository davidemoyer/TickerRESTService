package com.demoyer.tickerservice.response_builder;

import com.demoyer.tickerservice.delegate.CSVListUtils;
import com.demoyer.tickerservice.model.TickerDataProviderResponse;
import com.opencsv.CSVWriter;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;

import static com.demoyer.tickerservice.common.Constant.OUTPUT_DIRECTORY;

public class HTTPOutputResponseBuilder {

    public void createCSVStringFileOutputStream(TickerDataProviderResponse tickerDataProviderResponse,
                                                HttpServletResponse httpServletResponse) throws IOException {

        CSVListUtils csvListUtils = new CSVListUtils();
        OutputStream outputStream = httpServletResponse.getOutputStream();

        httpServletResponse.setContentType("text/csv");
        httpServletResponse.setHeader("Content-Disposition", "attachment; filename="
                + csvListUtils.buildCSVFileName(tickerDataProviderResponse));

        outputStream.write(csvListUtils.buildCSVString(tickerDataProviderResponse).getBytes());
        outputStream.flush();
        outputStream.close();
    }

    public void createCSVWriterOutputStream(TickerDataProviderResponse tickerDataProviderResponse) throws IOException {
        CSVListUtils csvListUtils = new CSVListUtils();
        CSVWriter csvWriter = new CSVWriter(
                new FileWriter(
                        OUTPUT_DIRECTORY
                                + csvListUtils.buildCSVFileName(tickerDataProviderResponse)));

        csvWriter.writeAll(csvListUtils.createCSVLineList(tickerDataProviderResponse));
        csvWriter.flush();
        csvWriter.close();
    }
}
