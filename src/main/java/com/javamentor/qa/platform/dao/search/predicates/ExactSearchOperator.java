package com.javamentor.qa.platform.dao.search.predicates;

import org.hibernate.search.engine.search.predicate.dsl.BooleanPredicateClausesStep;
import org.hibernate.search.engine.search.predicate.dsl.SearchPredicateFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ExactSearchOperator extends SearchOperator {
    protected ExactSearchOperator(@Value("exact search operator") String description,
                                  @Value("6") int order) {
        super(description, order);
    }

    @Override
    public BooleanPredicateClausesStep<?> parse(StringBuilder query, SearchPredicateFactory factory, BooleanPredicateClausesStep<?> booleanPredicate) {
        Pattern pattern = Pattern.compile("(\\\")([а-яА-Яa-zA-Z0-9\\s\\:\\?\\!\\$\\#\\_]+)(\\\")");
        Matcher matcher = pattern.matcher(query);

        if (matcher.find()) {
            BooleanPredicateClausesStep<?> innerBooleanPredicate = factory.bool()
                    .should(factory.phrase().field("title").matching(matcher.group(2)))
                    .should(factory.phrase().field("description").matching(matcher.group(2)))
                    .should(factory.phrase().field("htmlBody").matching(matcher.group(2)));
            booleanPredicate = booleanPredicate.must(innerBooleanPredicate);
        }

        query.replace(0, query.length(), matcher.replaceAll(""));

        return booleanPredicate;
    }
}
