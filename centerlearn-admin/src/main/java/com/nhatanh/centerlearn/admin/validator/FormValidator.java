package com.nhatanh.centerlearn.admin.validator;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EzySingleton
public class FormValidator {
    private final String EMAIL_PATTERN =  "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
        "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private final String PHONE_PATTERN = "^\\d{10}$";

    public boolean validateBlank(String str) {
        return str == null || str.isEmpty() ||
            str.chars().allMatch(Character::isWhitespace);
    }

    public boolean validateNull(Object obj) {
        return obj != null;
    }

    public boolean validateSpecialCharacter(String str) {
        String regex = "^[\\p{L}0-9\\s]+$";
        return str.matches(regex);
    }

    public boolean validateEmail(String email) {
        return email != null && email.matches(EMAIL_PATTERN);
    }

    public boolean validatePhoneNumber(String phone) {
        return phone != null && phone.matches(PHONE_PATTERN);
    }

    public boolean validateNumberPositive(int number) {
        return number > 0;
    }
    public boolean validateNumberPositive(long number) {
        return number >= 0;
    }
    public boolean validateId(long num) {
        return num > 0;
    }

    public <T> boolean isNull(T object) {
        return object == null;
    }
}
