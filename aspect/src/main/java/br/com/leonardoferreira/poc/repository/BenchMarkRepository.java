package br.com.leonardoferreira.poc.repository;

import br.com.leonardoferreira.poc.domain.BenchMark;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BenchMarkRepository extends CrudRepository<BenchMark, String> {
}
