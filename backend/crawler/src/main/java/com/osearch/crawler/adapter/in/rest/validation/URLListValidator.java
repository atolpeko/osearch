package com.osearch.crawler.adapter.in.rest.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.util.List;

import org.springframework.stereotype.Component;

/**
 * URLListValidator checks if the given list of URLs contains
 * any blanks, and returns true if all URLs are valid,
 * otherwise, it returns false and adds a constraint violation.
 */
@Component
public class URLListValidator implements ConstraintValidator<ValidURLList, List<String>> {

    @Override
    public boolean isValid(List<String> list, ConstraintValidatorContext context) {
        var hasBlankStrings = list.stream().anyMatch(String::isBlank);
        if (hasBlankStrings) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("URL list has blank URLs")
                .addConstraintViolation();
        }

        return !hasBlankStrings;
    }
}
