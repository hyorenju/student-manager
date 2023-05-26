package vn.edu.vnua.qlsvfita.validator.validation;

import vn.edu.vnua.qlsvfita.constant.DateTimeConstant;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class InputDateValidator implements ConstraintValidator<InputDate, String> {
    @Override
    public boolean isValid(String date, ConstraintValidatorContext constraintValidatorContext) {
        if (date == null)
            return false;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateTimeConstant.DATE_FORMAT);
        simpleDateFormat.setLenient(false);
        try {
            return simpleDateFormat.parse(date) != null;
        } catch (ParseException e) {
            return false;
        }
    }
}
