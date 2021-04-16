package com.javamentor.qa.platform.dao.search.predicates;

import org.hibernate.search.engine.search.predicate.dsl.BooleanPredicateClausesStep;
import org.hibernate.search.engine.search.predicate.dsl.SearchPredicateFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AuthorSearchOperator extends SearchOperator{
    protected AuthorSearchOperator(@Value("author search operator") String description,
                                   @Value("40") int order) {
        super(description, order);
    }

    @Override
    public BooleanPredicateClausesStep<?> parse(StringBuilder query, SearchPredicateFactory factory, BooleanPredicateClausesStep<?> booleanPredicate) {
        Pattern pattern = Pattern.compile("(user:)([0-9]+)");
        Matcher matcher = pattern.matcher(query);

        if (!matcher.find()) {
            return booleanPredicate;
        }

        matcher.reset();

        BooleanPredicateClausesStep<?> innerBooleanPredicate = factory.bool();

        while (matcher.find()) {
           long userId = Long.parseLong(matcher.group(2));
           innerBooleanPredicate = innerBooleanPredicate.should(factory.match().field("user.id").matching(userId));
        }

        booleanPredicate = booleanPredicate.must(innerBooleanPredicate);

        query.replace(0, query.length(), matcher.replaceAll(""));

        return booleanPredicate;
    }
}
