package com.javamentor.qa.platform.dao.search.predicates;

import com.javamentor.qa.platform.models.entity.user.User;
import org.hibernate.search.engine.search.predicate.dsl.BooleanPredicateClausesStep;
import org.hibernate.search.engine.search.predicate.dsl.SearchPredicateFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AuthorMeSearchOperator extends SearchOperator {
    protected AuthorMeSearchOperator(@Value("author is me search operator") String description,
                                     @Value("41") int order) {
        super(description, order);
    }

    @Override
    public BooleanPredicateClausesStep<?> parse(StringBuilder query, SearchPredicateFactory factory, BooleanPredicateClausesStep<?> b) {
        Pattern p = Pattern.compile("user:me");
        Matcher m = p.matcher(query);

        if (m.find()) {
            User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            long userId = currentUser.getId();
            b = b.must(factory.match().field("user.id").matching(userId));
        }

        query.replace(0, query.length(), m.replaceAll(""));

        return b;
    }
}
