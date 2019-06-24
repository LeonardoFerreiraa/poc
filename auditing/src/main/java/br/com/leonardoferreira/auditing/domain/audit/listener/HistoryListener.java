package br.com.leonardoferreira.auditing.domain.audit.listener;

import br.com.leonardoferreira.auditing.domain.audit.Revision;
import java.util.UUID;
import org.hibernate.envers.RevisionListener;

public class HistoryListener implements RevisionListener {

    @Override
    public void newRevision(final Object revisionEntity) {
        Revision revision = (Revision) revisionEntity;
        revision.setUuid(UUID.randomUUID().toString());
    }

}
