package com.javamentor.qa.platform.dao.search.predicates;

import org.hibernate.search.engine.search.predicate.dsl.BooleanPredicateClausesStep;
import org.hibernate.search.engine.search.predicate.dsl.SearchPredicateFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TagSearchOperator extends SearchOperator {

    public TagSearchOperator(@Value("[tag] search operator") String description,
                            @Value("30") int order) {
        super(description, order);
    }

    @Override
    public BooleanPredicateClausesStep<?> parse(StringBuilder query, SearchPredicateFactory factory, BooleanPredicateClausesStep<?> booleanPredicate) {
        Pattern pattern = Pattern.compile("(\\[)([a-zA-Z\\s\\-]+)(\\])");
        Matcher matcher = pattern.matcher(query);

        while (matcher.find()) {
            BooleanPredicateClausesStep<?> bool = factory.bool()
                    .should(factory.match().field("tags.name").matching(matcher.group(2)))
                    .should(factory.match().field("question.tags.name").matching(matcher.group(2)));
            booleanPredicate = booleanPredicate.must(bool);
        }


        query.replace(0, query.length(), matcher.replaceAll(""));

        return booleanPredicate;

    }
}
