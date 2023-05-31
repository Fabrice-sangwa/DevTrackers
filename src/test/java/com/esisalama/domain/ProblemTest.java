package com.esisalama.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.esisalama.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProblemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Problem.class);
        Problem problem1 = new Problem();
        problem1.setId(1L);
        Problem problem2 = new Problem();
        problem2.setId(problem1.getId());
        assertThat(problem1).isEqualTo(problem2);
        problem2.setId(2L);
        assertThat(problem1).isNotEqualTo(problem2);
        problem1.setId(null);
        assertThat(problem1).isNotEqualTo(problem2);
    }
}
