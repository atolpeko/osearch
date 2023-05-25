package com.osearch.crawler.util.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.util.List;

import org.springframework.stereotype.Component;

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
