package br.com.leonardoferreira.auditing.domain.audit.response;

import br.com.leonardoferreira.auditing.domain.response.ContactResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactHistoryResponse {

    private String type;

    private String auditDate;

    private String revision;

    private ContactResponse contact;

}
