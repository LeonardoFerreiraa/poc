package br.com.leonardoferreira.poc;

import java.util.StringJoiner;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
public class MethodLookup implements CommandLineRunner {

    private final DependencyA dependencyA;

    public MethodLookup(final DependencyA dependencyA) {
        this.dependencyA = dependencyA;
    }

    @Override
    public void run(final String... args) throws Exception {
        System.out.println(dependencyA.toString());
        System.out.println(dependencyA.toString());
        System.out.println(dependencyA.toString());
        System.out.println(dependencyA.toString());
        System.out.println(dependencyA.toString());
    }

    @Component
    static abstract class DependencyA {

        private final DependencyB dependencyB;

        private final DependencyC dependencyC;

        DependencyA(final DependencyB dependencyB,
                    final DependencyC dependencyC) {
            this.dependencyB = dependencyB;
            this.dependencyC = dependencyC;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", "DependencyA(", ")")
                    .add("hashCode=" + Integer.toHexString(hashCode()))
                    .add("dependencyB=" + dependencyB)
                    .add("dependencyC=" + dependencyC)
                    .add("createDependencyC=" + createDependencyC())
                    .toString();
        }

        @Lookup
        abstract DependencyC createDependencyC();

    }

    @Component
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
    static class DependencyB {

        @Override
        public String toString() {
            return new StringJoiner(", ", "DependencyB(", ")")
                    .add("hashCode=" + Integer.toHexString(hashCode()))
                    .toString();
        }

    }

    @Component
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    static class DependencyC {

        @Override
        public String toString() {
            return new StringJoiner(", ", "DependencyC(", ")")
                    .add("hashCode=" + Integer.toHexString(hashCode()))
                    .toString();
        }

    }
}

