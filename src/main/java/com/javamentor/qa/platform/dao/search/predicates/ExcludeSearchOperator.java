package com.javamentor.qa.platform.dao.search.predicates;

import org.hibernate.search.engine.search.predicate.dsl.BooleanPredicateClausesStep;
import org.hibernate.search.engine.search.predicate.dsl.SearchPredicateFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ExcludeSearchOperator extends SearchOperator {
    protected ExcludeSearchOperator(@Value("exclude (-) search operator") String description,
                                    @Value("5") int order) {
        super(description, order);
    }

    @Override
    public BooleanPredicateClausesStep<?> parse(StringBuilder query, SearchPredicateFactory factory, BooleanPredicateClausesStep<?> b) {
        Pattern p = Pattern.compile("(-)([а-яА-Яa-zA-Z0-9\\-]+)");
        Matcher m = p.matcher(query);

        while (m.find()) {
            BooleanPredicateClausesStep<?> bool = factory.bool()
                    .should(factory.match().field("title").matching(m.group(2)))
                    .should(factory.match().field("description").matching(m.group(2)))
                    .should(factory.match().field("htmlBody").matching(m.group(2)));
            b = b.mustNot(bool);
        }

        query.replace(0, query.length(), m.replaceAll(""));

        return b;
    }
}
