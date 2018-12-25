package br.com.leonardoferreira.poc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

/**
 * <pre>
 * table:
 * create table contact (
 *   id bigserial,
 *   name varchar(255),
 *   email varchar(255)
 * );
 * </pre>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@RequiredArgsConstructor
public class Contact {

    @Id
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String email;

}
