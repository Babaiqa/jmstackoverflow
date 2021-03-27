package com.javamentor.qa.platform.models.entity.user.reputation;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class ReputationValidator implements ConstraintValidator<CombinedNotNull, Reputation> {

    private String[] fields;

    @Override
    public void initialize(final CombinedNotNull combinedNotNull) {
        fields = combinedNotNull.fields();
    }

    @Override
    public boolean isValid(final Reputation reputation, final ConstraintValidatorContext context) {
        final BeanWrapperImpl beanWrapper = new BeanWrapperImpl(reputation);

        int oneOfField = 0;
        for (final String field : fields) {
            final Object fieldValue = beanWrapper.getPropertyValue(field);
            if (fieldValue != null) {
                oneOfField++;
            }
        }

        return oneOfField == 1;
    }
}
