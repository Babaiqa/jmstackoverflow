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
    public BooleanPredicateClausesStep<?> parse(StringBuilder query, SearchPredicateFactory factory, BooleanPredicateClausesStep<?> b) {
        Pattern p = Pattern.compile("(\\\")([а-яА-Яa-zA-Z0-9\\s\\:\\?\\!\\$\\#\\_]+)(\\\")");
        Matcher m = p.matcher(query);

        if (m.find()) {
            BooleanPredicateClausesStep<?> bool = factory.bool()
                    .should(factory.phrase().field("title").matching(m.group(2)))
                    .should(factory.phrase().field("description").matching(m.group(2)))
                    .should(factory.phrase().field("htmlBody").matching(m.group(2)));
            b = b.must(bool);
        }

        query.replace(0, query.length(), m.replaceAll(""));

        return b;
    }
}
