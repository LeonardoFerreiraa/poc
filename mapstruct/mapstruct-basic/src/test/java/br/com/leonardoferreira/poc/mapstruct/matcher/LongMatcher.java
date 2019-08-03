package br.com.leonardoferreira.poc.mapstruct.matcher;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LongMatcher extends BaseMatcher<Long> {

    private Long expected;

    public static LongMatcher is(final Long expected) {
        return new LongMatcher(expected);
    }

    @Override
    public boolean matches(final Object item) {
        return item != null && expected.equals(Long.parseLong(item.toString()));
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("value should be equal to ").appendText(String.valueOf(expected));
    }
}
