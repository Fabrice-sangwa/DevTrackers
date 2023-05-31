package com.esisalama.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DashboardMapperTest {

    private DashboardMapper dashboardMapper;

    @BeforeEach
    public void setUp() {
        dashboardMapper = new DashboardMapperImpl();
    }
}
