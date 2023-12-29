package com.hepsiemlak.todo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.couchbase.core.mapping.event.ValidatingCouchbaseEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class CouchBaseValidation {

	@Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public ValidatingCouchbaseEventListener validationEventListener() {
        return new ValidatingCouchbaseEventListener(validator());
    }

}
