package com.graphQl.demo.exception.advice;


import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.graphql.execution.DataFetcherExceptionResolver;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;


public class GraphQLExceptionResolver implements DataFetcherExceptionResolver {

    @Override
    public Mono<List<GraphQLError>> resolveException(Throwable ex, DataFetchingEnvironment env) {

        if (ex instanceof ConstraintViolationException violationEx) {
            List<GraphQLError> errors = violationEx.getConstraintViolations().stream()
                    .map(violation -> GraphqlErrorBuilder.newError()
                            .message(violation.getPropertyPath() + ": " + violation.getMessage())
                            .build())
                    .collect(Collectors.toList());
            return Mono.just(errors);
        }

        if (ex instanceof EntityNotFoundException entityEx) {
            GraphQLError error = GraphqlErrorBuilder.newError()
                    .message(entityEx.getMessage())
                    .build();
            return Mono.just(List.of(error));
        }

        GraphQLError genericError = GraphqlErrorBuilder.newError()
                .message("Erreur interne : " + ex.getMessage())
                .build();
        return Mono.just(List.of(genericError));
    }
}
