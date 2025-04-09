package com.graphQl.demo.ScalarTypes;

/*  Note that there is a library you can use that have ready to use types
*   No need to recreate the boilerplate code
*   However I think it is good to do create and understand how it is done
*  */

import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Locale;



public class DateScalar {

       /*  Note that there is a library you can use that have ready to use types
        *   No need to recreate the boilerplate code
        *   However I think it is good to do create and understand how it is done
        */

        public static final GraphQLScalarType DATE = GraphQLScalarType.newScalar()
                .name("Date")
                .description("A custom scalar that handles dates")
                .coercing(new Coercing() {

                    // When graphQl has to turn LocalDate Type into json
                    @Override
                    public Object serialize(Object dataFetcherResult, GraphQLContext graphQLContext, Locale locale) {
                        return ((LocalDate) dataFetcherResult).toString();
                    }
                    // when we send data in variables
                    @Override
                    public Object parseValue(Object input, GraphQLContext graphQLContext, Locale locale) {
                        return LocalDate.parse(input.toString());
                    }
                    // hard coded
                    @Override
                    public Object parseLiteral(Value input, CoercedVariables variables, GraphQLContext graphQLContext, Locale locale) {
                        if (input instanceof StringValue stringValue) {
                            return LocalDate.parse(stringValue.getValue());
                        }
                        throw new CoercingParseLiteralException("Invalid literal");
                    }
                })
                .build();

}


