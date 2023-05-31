package com.esisalama.repository;

import com.esisalama.domain.Message;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class MessageRepositoryWithBagRelationshipsImpl implements MessageRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Message> fetchBagRelationships(Optional<Message> message) {
        return message.map(this::fetchSendees);
    }

    @Override
    public Page<Message> fetchBagRelationships(Page<Message> messages) {
        return new PageImpl<>(fetchBagRelationships(messages.getContent()), messages.getPageable(), messages.getTotalElements());
    }

    @Override
    public List<Message> fetchBagRelationships(List<Message> messages) {
        return Optional.of(messages).map(this::fetchSendees).orElse(Collections.emptyList());
    }

    Message fetchSendees(Message result) {
        return entityManager
            .createQuery("select message from Message message left join fetch message.sendees where message is :message", Message.class)
            .setParameter("message", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Message> fetchSendees(List<Message> messages) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, messages.size()).forEach(index -> order.put(messages.get(index).getId(), index));
        List<Message> result = entityManager
            .createQuery(
                "select distinct message from Message message left join fetch message.sendees where message in :messages",
                Message.class
            )
            .setParameter("messages", messages)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
