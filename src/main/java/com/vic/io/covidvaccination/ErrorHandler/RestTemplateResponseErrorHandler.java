package com.vic.io.covidvaccination.ErrorHandler;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

@Component
@Log4j2
public class RestTemplateResponseErrorHandler
        implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse)
            throws IOException {

        return (
                httpResponse.getStatusCode().series() == CLIENT_ERROR
                        || httpResponse.getStatusCode().series() == SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse)
            throws IOException {

        if (httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {
           log.info("server error");
        } else if (httpResponse.getStatusCode().series() == CLIENT_ERROR) {
            log.info("client error");
            if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "entity not found"
                );
            }
        }
    }
}