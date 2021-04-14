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
    public BooleanPredicateClausesStep<?> parse(StringBuilder query, SearchPredicateFactory factory, BooleanPredicateClausesStep<?> b) {

        if (query.toString().trim().isEmpty()) {
            return b;
        }

        MatchPredicateOptionsStep<?> p1 = factory.match().field("description")
                .matching(query.toString());

        MatchPredicateOptionsStep<?> p2 = factory.match().field("title")
                .matching(query.toString());

        MatchPredicateOptionsStep<?> p3 = factory.match().field("htmlBody")
                .matching(query.toString());

        BooleanPredicateClausesStep<?> bool = factory.bool()
                .should(p1)
                .should(p2)
                .should(p3);

        b = b.must(bool);

        return b;
    }
}
