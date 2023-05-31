package com.esisalama.repository;

import com.esisalama.domain.Problem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ProblemRepositoryWithBagRelationships {
    Optional<Problem> fetchBagRelationships(Optional<Problem> problem);

    List<Problem> fetchBagRelationships(List<Problem> problems);

    Page<Problem> fetchBagRelationships(Page<Problem> problems);
}
