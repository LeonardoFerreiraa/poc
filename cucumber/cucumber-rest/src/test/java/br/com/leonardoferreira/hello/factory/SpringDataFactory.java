package br.com.leonardoferreira.hello.factory;

import br.com.leonardoferreira.jbacon.JBacon;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Optional;

@Component
public abstract class SpringDataFactory<T> extends JBacon<T> implements ApplicationContextAware {

    private CrudRepository<T, Serializable> repository;

    @Override
    @SuppressWarnings("unchecked")
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Repositories repositories = new Repositories(applicationContext);
        Optional<Object> repositoryFor = repositories.getRepositoryFor(getDefault().getClass());
        this.repository = (CrudRepository<T, Serializable>) repositoryFor.orElse(null);
    }

    @Override
    protected abstract T getDefault();

    @Override
    protected abstract T getEmpty();

    @Override
    protected void persist(T t) {
        this.repository.save(t);
    }
}
