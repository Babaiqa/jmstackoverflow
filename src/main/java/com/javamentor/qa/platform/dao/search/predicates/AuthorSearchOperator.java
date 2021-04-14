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
    public BooleanPredicateClausesStep<?> parse(StringBuilder query, SearchPredicateFactory factory, BooleanPredicateClausesStep<?> b) {
        Pattern p = Pattern.compile("(user:)([0-9]+)");
        Matcher m = p.matcher(query);

        if (!m.find()) {
            return b;
        }

        m.reset();

        BooleanPredicateClausesStep<?> bool = factory.bool();

        while (m.find()) {
           long userId = Long.parseLong(m.group(2));
           bool = bool.should(factory.match().field("user.id").matching(userId));
        }

        b = b.must(bool);

        query.replace(0, query.length(), m.replaceAll(""));

        return b;
    }
}
