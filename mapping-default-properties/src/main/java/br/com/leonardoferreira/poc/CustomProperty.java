package br.com.leonardoferreira.poc;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "custom")
public class CustomProperty {

    private String firstProperty;

    private boolean secondProperty = false;

    private int thirdProperty = 300;

    private Long forthProperty;

    @Override
    public String toString() {
        return "CustomProperty{" +
                "firstProperty='" + firstProperty + '\'' +
                ", secondProperty=" + secondProperty +
                ", thirdProperty=" + thirdProperty +
                ", forthProperty=" + forthProperty +
                '}';
    }

    public String getFirstProperty() {
        return firstProperty;
    }

    public void setFirstProperty(final String firstProperty) {
        this.firstProperty = firstProperty;
    }

    public boolean isSecondProperty() {
        return secondProperty;
    }

    public void setSecondProperty(final boolean secondProperty) {
        this.secondProperty = secondProperty;
    }

    public int getThirdProperty() {
        return thirdProperty;
    }

    public void setThirdProperty(final int thirdProperty) {
        this.thirdProperty = thirdProperty;
    }

    public Long getForthProperty() {
        return forthProperty;
    }

    public void setForthProperty(final Long forthProperty) {
        this.forthProperty = forthProperty;
    }
}
