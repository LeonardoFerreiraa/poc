package br.com.leonardoferreira.poc.servicecall.service;

import br.com.leonardoferreira.poc.servicecall.adapter.impl.RawResponseAdapter;
import br.com.leonardoferreira.poc.servicecall.domain.Contact;
import br.com.leonardoferreira.poc.servicecall.domain.Phone;
import br.com.leonardoferreira.poc.servicecall.domain.request.PhoneRequest;
import br.com.leonardoferreira.poc.servicecall.domain.response.PhoneResponse;
import br.com.leonardoferreira.poc.servicecall.exception.ResourceNotFound;
import br.com.leonardoferreira.poc.servicecall.mapper.PhoneMapper;
import br.com.leonardoferreira.poc.servicecall.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PhoneService {

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private PhoneMapper phoneMapper;

    @Autowired
    private ContactService contactService;

    @Transactional(readOnly = true)
    public List<PhoneResponse> findAll() {
        List<Phone> phones = phoneRepository.findAll();
        return phoneMapper.responsesFromPhones(phones);
    }

    @Transactional(readOnly = true)
    public PhoneResponse findById(Long id) {
        Phone phone = phoneRepository.findById(id).orElseThrow(ResourceNotFound::new);
        return phoneMapper.responseFromPhone(phone);
    }

    @Transactional
    public Long created(PhoneRequest phoneRequest) {
        Phone phone = phoneMapper.phoneFromRequest(phoneRequest);
        phoneRepository.save(phone);
        return phone.getId();
    }

    @Transactional(readOnly = true)
    public boolean containsPhone(PhoneRequest phoneRequest) {
        Contact contact = contactService.findById(phoneRequest.getContact(), new RawResponseAdapter<>());
        return phoneRepository.existsByNumberAndContact(phoneRequest.getNumber(), contact);
    }

}
