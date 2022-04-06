package br.com.leonardoferreira.poc;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.config.ConfigDataResource;

@Data
@EqualsAndHashCode(callSuper = true)
public class MyConfigDataResource extends ConfigDataResource {
}
