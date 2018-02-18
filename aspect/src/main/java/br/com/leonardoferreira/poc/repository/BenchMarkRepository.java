package br.com.leonardoferreira.poc.repository;

import br.com.leonardoferreira.poc.domain.BenchMark;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BenchMarkRepository extends MongoRepository<BenchMark, String> {
}
