package com.esisalama.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.esisalama.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DashboardDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DashboardDTO.class);
        DashboardDTO dashboardDTO1 = new DashboardDTO();
        dashboardDTO1.setId(1L);
        DashboardDTO dashboardDTO2 = new DashboardDTO();
        assertThat(dashboardDTO1).isNotEqualTo(dashboardDTO2);
        dashboardDTO2.setId(dashboardDTO1.getId());
        assertThat(dashboardDTO1).isEqualTo(dashboardDTO2);
        dashboardDTO2.setId(2L);
        assertThat(dashboardDTO1).isNotEqualTo(dashboardDTO2);
        dashboardDTO1.setId(null);
        assertThat(dashboardDTO1).isNotEqualTo(dashboardDTO2);
    }
}
