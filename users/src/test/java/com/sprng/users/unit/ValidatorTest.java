package com.sprng.users.unit;

import com.sprng.users.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("OurTEST")
public class ValidatorTest {
    @Test
//    public void isValidStringViewErrorInMin
//    public void isValidStringInvalidArguments
    public void isValidString() {
//       assertTrue(Validator.isValidString("hello",1,10));
        assertAll("assertGroupOur",
                () -> assertTrue(Validator.isValidString("hello", 1, 10)),
                () -> assertTrue(Validator.isValidString("12344str", 1, 10))
        );
    }

@Test
    public void invalidStringByMinParam() {
        assertAll("assertGroupOur",
                () -> assertFalse(Validator.isValidString("he", 10, 10)),
                () -> assertFalse(Validator.isValidString("str", 10, 10))
        );
    }
}
