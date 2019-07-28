package com.gluco.diary.gateway.config;

import com.gluco.diary.gateway.client.exception.AuthException;
import com.gluco.diary.gateway.client.exception.UnknownException;

import feign.Response;
import feign.codec.ErrorDecoder;

public class StashErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() >= 400 && response.status() <= 499) {
            return new AuthException("Auth Error");
        }
        if (response.status() >= 500 && response.status() <= 599) {
            return new UnknownException("OOps. Something went wrong. Please try Later.");
        }
        return decode(methodKey, response);
    }
}