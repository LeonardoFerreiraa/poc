package br.com.leonardoferreira.poc;

import java.util.List;

import org.springframework.boot.context.config.ConfigDataLocation;
import org.springframework.boot.context.config.ConfigDataLocationNotFoundException;
import org.springframework.boot.context.config.ConfigDataLocationResolver;
import org.springframework.boot.context.config.ConfigDataLocationResolverContext;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;

public class MyConfigDataLocationResolver implements ConfigDataLocationResolver<MyConfigDataResource> {

    @Override
    public boolean isResolvable(final ConfigDataLocationResolverContext context, final ConfigDataLocation location) {
        return location.hasPrefix("myrc:");
    }

    @Override
    public List<MyConfigDataResource> resolve(final ConfigDataLocationResolverContext context, final ConfigDataLocation location)
            throws ConfigDataLocationNotFoundException, ConfigDataResourceNotFoundException {
        return List.of(new MyConfigDataResource());
    }

}
