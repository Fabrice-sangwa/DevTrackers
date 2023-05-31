package com.esisalama.repository;

import com.esisalama.domain.Problem;
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
public class ProblemRepositoryWithBagRelationshipsImpl implements ProblemRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Problem> fetchBagRelationships(Optional<Problem> problem) {
        return problem.map(this::fetchUsers);
    }

    @Override
    public Page<Problem> fetchBagRelationships(Page<Problem> problems) {
        return new PageImpl<>(fetchBagRelationships(problems.getContent()), problems.getPageable(), problems.getTotalElements());
    }

    @Override
    public List<Problem> fetchBagRelationships(List<Problem> problems) {
        return Optional.of(problems).map(this::fetchUsers).orElse(Collections.emptyList());
    }

    Problem fetchUsers(Problem result) {
        return entityManager
            .createQuery("select problem from Problem problem left join fetch problem.users where problem is :problem", Problem.class)
            .setParameter("problem", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Problem> fetchUsers(List<Problem> problems) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, problems.size()).forEach(index -> order.put(problems.get(index).getId(), index));
        List<Problem> result = entityManager
            .createQuery(
                "select distinct problem from Problem problem left join fetch problem.users where problem in :problems",
                Problem.class
            )
            .setParameter("problems", problems)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
