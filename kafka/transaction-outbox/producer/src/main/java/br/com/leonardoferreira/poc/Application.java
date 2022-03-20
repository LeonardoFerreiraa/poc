package br.com.leonardoferreira.poc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@EnableScheduling
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

@Component
@RequiredArgsConstructor
class MyScheduler {

    private final OutboxEntryService outboxEntryService;

    @Scheduled(cron = "0 0/1 * 1/1 * ?")
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void job() {
        final String jobId = UUID.randomUUID().toString();
        List<OutboxEntry> entries = outboxEntryService.acquire(jobId, 2);
        entries.forEach(outboxEntryService::proccess);
    }

}

@RestController
@RequiredArgsConstructor
class MyController {

    private final MyService myService;

    @GetMapping("/produce/{tiny}")
    public void send(@PathVariable final String tiny) {
        myService.persist(tiny);
    }

}

@Getter
@RequiredArgsConstructor
enum EventType {

    SIMPLE("simple.topic");

    private final String topic;

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class SimpleMessage {
    private String id;
}

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
class MyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String simpleField;

    @Column(length = 5)
    private String tinyField;

}

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
class OutboxEntry {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    private String className;

    private String body;

    private String jobId;

    private LocalDateTime acquiredAt;

}

@Service
@RequiredArgsConstructor
class MyService {

    private final MyEntityRepository repository;

    private final OutboxEntryService outboxEntryService;

    @Transactional
    public void persist(final String tinyField) {
        final MyEntity myEntity = MyEntity.builder()
                .tinyField(tinyField)
                .simpleField(UUID.randomUUID().toString())
                .build();

        outboxEntryService.save(EventType.SIMPLE, new SimpleMessage(myEntity.getSimpleField()));
        repository.save(myEntity);
    }

}

@Slf4j
@Service
@RequiredArgsConstructor
class OutboxEntryService {

    private final OutboxEntryRepository repository;

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<Object, Object> kafkaTemplate;

    @SneakyThrows
    @Transactional
    public <T> void save(final EventType eventType, final T body) {
        final OutboxEntry outboxEntry = OutboxEntry.builder()
                .eventType(eventType)
                .className(body.getClass().getName())
                .body(objectMapper.writeValueAsString(body))
                .build();

        repository.save(outboxEntry);
    }

    @SneakyThrows
    void proccess(final OutboxEntry outboxEntry) {
        log.info("M=proccess, outboxEntry={}", outboxEntry);

        kafkaTemplate.send(
                outboxEntry.getEventType().getTopic(),
                objectMapper.readValue(
                        outboxEntry.getBody(),
                        Class.forName(outboxEntry.getClassName())
                )
        ).addCallback(
                result -> repository.delete(outboxEntry),
                ex -> repository.clearJobId(outboxEntry.getId())
        );
    }

    @Transactional
    public List<OutboxEntry> acquire(final String jobId, final int amount) {
        repository.acquire(jobId, amount);
        return repository.findByJobId(jobId);
    }

}

@Repository
interface MyEntityRepository extends CrudRepository<MyEntity, Long> {
}

@Repository
interface OutboxEntryRepository extends CrudRepository<OutboxEntry, Long> {

    @Modifying
    @Query(
            value = """
                    update outbox_entry 
                    set job_id = null, 
                        acquired_at = null 
                    where id = :id""",
            nativeQuery = true
    )
    void clearJobId(Long id);

    @Modifying
    @Query(
            value = """
                    update outbox_entry
                    set job_id = :jobId,
                        acquired_at = now()
                    where id in (
                         select id 
                         from outbox_entry
                         where  job_id is null
                                or acquired_at + interval '5 minutes' <= now()
                         limit  :amount
                         for update skip locked
                     );
                    """,
            nativeQuery = true
    )
    void acquire(String jobId, int amount);

    List<OutboxEntry> findByJobId(String jobId);

}




