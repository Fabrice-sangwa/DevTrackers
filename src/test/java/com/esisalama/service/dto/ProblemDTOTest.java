package com.esisalama.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.esisalama.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProblemDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProblemDTO.class);
        ProblemDTO problemDTO1 = new ProblemDTO();
        problemDTO1.setId(1L);
        ProblemDTO problemDTO2 = new ProblemDTO();
        assertThat(problemDTO1).isNotEqualTo(problemDTO2);
        problemDTO2.setId(problemDTO1.getId());
        assertThat(problemDTO1).isEqualTo(problemDTO2);
        problemDTO2.setId(2L);
        assertThat(problemDTO1).isNotEqualTo(problemDTO2);
        problemDTO1.setId(null);
        assertThat(problemDTO1).isNotEqualTo(problemDTO2);
    }
}
