package br.com.leonardoferreira.test.domain;

import br.com.leonardoferreira.test.assertion.ValidationAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by lferreira on 2/13/18
 */
@RunWith(SpringRunner.class)
public class ContactTest {

    @Test
    public void validations() {
        ValidationAssert.assertThat(Contact.class)
                .hasNotNullIn("name")
                .hasNotEmptyIn("email", "Email can't be empty");
    }

}
