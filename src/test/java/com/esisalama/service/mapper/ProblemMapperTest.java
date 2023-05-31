package com.esisalama.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProblemMapperTest {

    private ProblemMapper problemMapper;

    @BeforeEach
    public void setUp() {
        problemMapper = new ProblemMapperImpl();
    }
}
