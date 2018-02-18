package br.com.leonardoferreira.poc.service;

import br.com.leonardoferreira.poc.domain.BenchMark;
import br.com.leonardoferreira.poc.repository.BenchMarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BenchMarkService {
    @Autowired
    private BenchMarkRepository benchMarkRepository;
    public void create(String methodName, Long executionTime) {
        BenchMark benchMark = new BenchMark();
        benchMark.setExecutionTime(executionTime);
        benchMark.setMethodName(methodName);

        benchMarkRepository.save(benchMark);
    }
}
