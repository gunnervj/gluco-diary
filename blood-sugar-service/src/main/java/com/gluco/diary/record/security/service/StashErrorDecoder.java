package com.gluco.diary.record.security.service;

import com.gluco.diary.record.api.constants.ERROR_CODES;
import com.gluco.diary.record.api.exceptions.AuthException;
import com.gluco.diary.record.api.exceptions.UnknownException;

import feign.Response;
import feign.codec.ErrorDecoder;

public class StashErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() >= 400 && response.status() <= 499) {
        	System.out.println("response => " +response.status());
            return new AuthException(ERROR_CODES.INVALID_TOKEN);
        }
        if (response.status() >= 500 && response.status() <= 599) {
            return new UnknownException(ERROR_CODES.UNDER_MAINTENANCE);
        }
        return decode(methodKey, response);
    }
}