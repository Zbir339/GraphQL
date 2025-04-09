package com.graphQl.demo.config;


import com.graphQl.demo.ScalarTypes.DateScalar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class GraphQlConfigScalar {

    /*
    *  In here we add our scalar types
    *  Simply put we wire our custom types
    * */

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder.scalar(DateScalar.DATE);
    }

}
