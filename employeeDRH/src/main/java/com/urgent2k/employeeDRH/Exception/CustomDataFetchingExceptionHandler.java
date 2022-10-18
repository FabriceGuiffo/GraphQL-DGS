package com.urgent2k.employeeDRH.Exception;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.netflix.graphql.types.errors.TypedGraphQLError;
import com.urgent2k.employeeDRH.Security.JwtProperties;
import graphql.GraphQLError;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
public class CustomDataFetchingExceptionHandler implements DataFetcherExceptionHandler {
    @Override
    public CompletableFuture<DataFetcherExceptionHandlerResult> handleException(DataFetcherExceptionHandlerParameters handlerParameters) {
        if(handlerParameters.getException() instanceof EmployeeNotFoundException){
            Map<String,Object> debugInfo= new HashMap<>();
            debugInfo.put("What went wrong","check the Fetcher.Query.findParticularEmployee");

            GraphQLError graphQLError= TypedGraphQLError.newNotFoundBuilder()
                    .message(handlerParameters.getException().getMessage())
                    .debugInfo(debugInfo)
                    .path(handlerParameters.getPath())
                    .build();

            DataFetcherExceptionHandlerResult result=DataFetcherExceptionHandlerResult.newResult()
                    .error(graphQLError)
                    .build();
            return CompletableFuture.completedFuture(result);
        }
        else if(handlerParameters.getException() instanceof CompanyNotFoundException){
            Map<String,Object> debugInfo= new HashMap<>();
            debugInfo.put("What went wrong","check the Fetcher.Query.findCompany");

            GraphQLError graphQLError= TypedGraphQLError.newNotFoundBuilder()
                    .message(handlerParameters.getException().getMessage())
                    .debugInfo(debugInfo)
                    .path(handlerParameters.getPath())
                    .build();

            DataFetcherExceptionHandlerResult result=DataFetcherExceptionHandlerResult.newResult()
                    .error(graphQLError)
                    .build();
            return CompletableFuture.completedFuture(result);

        }
        else if(handlerParameters.getException() instanceof TokenExpiredException){
            Map<String,Object> debugInfo= new HashMap<>();
            debugInfo.put("What went wrong","Your token expired it is valid only "+ JwtProperties.EXPIRATION_TIME+"millis");

            GraphQLError graphQLError= TypedGraphQLError.newPermissionDeniedBuilder()
                    .message(handlerParameters.getException().getMessage())
                    .debugInfo(debugInfo)
                    .path(handlerParameters.getPath())
                    .build();

            DataFetcherExceptionHandlerResult result=DataFetcherExceptionHandlerResult.newResult()
                    .error(graphQLError)
                    .build();
            return CompletableFuture.completedFuture(result);

        }
        else{
            return DataFetcherExceptionHandler.super.handleException(handlerParameters);
        }
    }
}
