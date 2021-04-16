package com.javamentor.qa.platform.dao.search.predicates;

import org.hibernate.search.engine.search.predicate.dsl.BooleanPredicateClausesStep;
import org.hibernate.search.engine.search.predicate.dsl.MatchPredicateOptionsStep;
import org.hibernate.search.engine.search.predicate.dsl.SearchPredicateFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NoSearchOperator extends SearchOperator {

    public NoSearchOperator(@Value("clean query string without search operators") String description,
                            @Value("100") int order) {
        super(description, order);
    }

    @Override
    public BooleanPredicateClausesStep<?> parse(StringBuilder query, SearchPredicateFactory factory, BooleanPredicateClausesStep<?> booleanPredicate) {

        if (query.toString().trim().isEmpty()) {
            return booleanPredicate;
        }

        MatchPredicateOptionsStep<?> descriptionPredicateOption = factory.match().field("description")
                .matching(query.toString());

        MatchPredicateOptionsStep<?> titlePredicateOption = factory.match().field("title")
                .matching(query.toString());

        MatchPredicateOptionsStep<?> htmlBodyPredicateOption = factory.match().field("htmlBody")
                .matching(query.toString());

        BooleanPredicateClausesStep<?> innerBooleanPredicate = factory.bool()
                .should(descriptionPredicateOption)
                .should(titlePredicateOption)
                .should(htmlBodyPredicateOption);

        booleanPredicate = booleanPredicate.must(innerBooleanPredicate);

        return booleanPredicate;
    }
}
