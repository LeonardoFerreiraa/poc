package br.com.leonardoferreira.hello.factory;

import br.com.leonardoferreira.hello.utils.BeanUtils;
import com.github.javafaker.Faker;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by lferreira on 7/1/17.
 */
public abstract class AbstractFactory<T, ID extends Serializable> {
    protected final static Faker faker = new Faker();

    public T build() {
        return build(null);
    }

    public T build(T original) {
        if (original == null) original = getEmpty();

        final T t = getDefault();
        BeanUtils.copyPropertiesNotNull(t, original);
        return original;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public T create() {
        final T t = build();
        getRepository().save(t);
        return t;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public T create(T original) {
        original = build(original);
        getRepository().save(original);
        return original;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<T> create(final int count) {
        return IntStream.range(0, count).boxed()
            .map(i -> create())
            .collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<T> create(final int count, final T original) {
        return IntStream.range(0, count).boxed()
            .map(i -> {
                T empty = getEmpty();
                BeanUtils.copyPropertiesNotNull(original, empty);
                return create(empty);
            })
            .collect(Collectors.toList());
    }

    protected abstract T getDefault();

    protected abstract T getEmpty();

    protected abstract CrudRepository<T, ID> getRepository();
}
