package com.esisalama.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.esisalama.IntegrationTest;
import com.esisalama.domain.Problem;
import com.esisalama.domain.enumeration.Priority;
import com.esisalama.domain.enumeration.State;
import com.esisalama.repository.ProblemRepository;
import com.esisalama.service.ProblemService;
import com.esisalama.service.dto.ProblemDTO;
import com.esisalama.service.mapper.ProblemMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProblemResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProblemResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final State DEFAULT_STATE = State.EN_COURS;
    private static final State UPDATED_STATE = State.EN_PAUSE;

    private static final Priority DEFAULT_PRIORITY = Priority.URGENT;
    private static final Priority UPDATED_PRIORITY = Priority.MOYEN;

    private static final String ENTITY_API_URL = "/api/problems";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProblemRepository problemRepository;

    @Mock
    private ProblemRepository problemRepositoryMock;

    @Autowired
    private ProblemMapper problemMapper;

    @Mock
    private ProblemService problemServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProblemMockMvc;

    private Problem problem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Problem createEntity(EntityManager em) {
        Problem problem = new Problem().description(DEFAULT_DESCRIPTION).state(DEFAULT_STATE).priority(DEFAULT_PRIORITY);
        return problem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Problem createUpdatedEntity(EntityManager em) {
        Problem problem = new Problem().description(UPDATED_DESCRIPTION).state(UPDATED_STATE).priority(UPDATED_PRIORITY);
        return problem;
    }

    @BeforeEach
    public void initTest() {
        problem = createEntity(em);
    }

    @Test
    @Transactional
    void createProblem() throws Exception {
        int databaseSizeBeforeCreate = problemRepository.findAll().size();
        // Create the Problem
        ProblemDTO problemDTO = problemMapper.toDto(problem);
        restProblemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(problemDTO)))
            .andExpect(status().isCreated());

        // Validate the Problem in the database
        List<Problem> problemList = problemRepository.findAll();
        assertThat(problemList).hasSize(databaseSizeBeforeCreate + 1);
        Problem testProblem = problemList.get(problemList.size() - 1);
        assertThat(testProblem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProblem.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testProblem.getPriority()).isEqualTo(DEFAULT_PRIORITY);
    }

    @Test
    @Transactional
    void createProblemWithExistingId() throws Exception {
        // Create the Problem with an existing ID
        problem.setId(1L);
        ProblemDTO problemDTO = problemMapper.toDto(problem);

        int databaseSizeBeforeCreate = problemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProblemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(problemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Problem in the database
        List<Problem> problemList = problemRepository.findAll();
        assertThat(problemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProblems() throws Exception {
        // Initialize the database
        problemRepository.saveAndFlush(problem);

        // Get all the problemList
        restProblemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(problem.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProblemsWithEagerRelationshipsIsEnabled() throws Exception {
        when(problemServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProblemMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(problemServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProblemsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(problemServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProblemMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(problemRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getProblem() throws Exception {
        // Initialize the database
        problemRepository.saveAndFlush(problem);

        // Get the problem
        restProblemMockMvc
            .perform(get(ENTITY_API_URL_ID, problem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(problem.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProblem() throws Exception {
        // Get the problem
        restProblemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProblem() throws Exception {
        // Initialize the database
        problemRepository.saveAndFlush(problem);

        int databaseSizeBeforeUpdate = problemRepository.findAll().size();

        // Update the problem
        Problem updatedProblem = problemRepository.findById(problem.getId()).get();
        // Disconnect from session so that the updates on updatedProblem are not directly saved in db
        em.detach(updatedProblem);
        updatedProblem.description(UPDATED_DESCRIPTION).state(UPDATED_STATE).priority(UPDATED_PRIORITY);
        ProblemDTO problemDTO = problemMapper.toDto(updatedProblem);

        restProblemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, problemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(problemDTO))
            )
            .andExpect(status().isOk());

        // Validate the Problem in the database
        List<Problem> problemList = problemRepository.findAll();
        assertThat(problemList).hasSize(databaseSizeBeforeUpdate);
        Problem testProblem = problemList.get(problemList.size() - 1);
        assertThat(testProblem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProblem.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testProblem.getPriority()).isEqualTo(UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void putNonExistingProblem() throws Exception {
        int databaseSizeBeforeUpdate = problemRepository.findAll().size();
        problem.setId(count.incrementAndGet());

        // Create the Problem
        ProblemDTO problemDTO = problemMapper.toDto(problem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProblemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, problemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(problemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Problem in the database
        List<Problem> problemList = problemRepository.findAll();
        assertThat(problemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProblem() throws Exception {
        int databaseSizeBeforeUpdate = problemRepository.findAll().size();
        problem.setId(count.incrementAndGet());

        // Create the Problem
        ProblemDTO problemDTO = problemMapper.toDto(problem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProblemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(problemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Problem in the database
        List<Problem> problemList = problemRepository.findAll();
        assertThat(problemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProblem() throws Exception {
        int databaseSizeBeforeUpdate = problemRepository.findAll().size();
        problem.setId(count.incrementAndGet());

        // Create the Problem
        ProblemDTO problemDTO = problemMapper.toDto(problem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProblemMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(problemDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Problem in the database
        List<Problem> problemList = problemRepository.findAll();
        assertThat(problemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProblemWithPatch() throws Exception {
        // Initialize the database
        problemRepository.saveAndFlush(problem);

        int databaseSizeBeforeUpdate = problemRepository.findAll().size();

        // Update the problem using partial update
        Problem partialUpdatedProblem = new Problem();
        partialUpdatedProblem.setId(problem.getId());

        restProblemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProblem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProblem))
            )
            .andExpect(status().isOk());

        // Validate the Problem in the database
        List<Problem> problemList = problemRepository.findAll();
        assertThat(problemList).hasSize(databaseSizeBeforeUpdate);
        Problem testProblem = problemList.get(problemList.size() - 1);
        assertThat(testProblem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProblem.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testProblem.getPriority()).isEqualTo(DEFAULT_PRIORITY);
    }

    @Test
    @Transactional
    void fullUpdateProblemWithPatch() throws Exception {
        // Initialize the database
        problemRepository.saveAndFlush(problem);

        int databaseSizeBeforeUpdate = problemRepository.findAll().size();

        // Update the problem using partial update
        Problem partialUpdatedProblem = new Problem();
        partialUpdatedProblem.setId(problem.getId());

        partialUpdatedProblem.description(UPDATED_DESCRIPTION).state(UPDATED_STATE).priority(UPDATED_PRIORITY);

        restProblemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProblem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProblem))
            )
            .andExpect(status().isOk());

        // Validate the Problem in the database
        List<Problem> problemList = problemRepository.findAll();
        assertThat(problemList).hasSize(databaseSizeBeforeUpdate);
        Problem testProblem = problemList.get(problemList.size() - 1);
        assertThat(testProblem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProblem.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testProblem.getPriority()).isEqualTo(UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void patchNonExistingProblem() throws Exception {
        int databaseSizeBeforeUpdate = problemRepository.findAll().size();
        problem.setId(count.incrementAndGet());

        // Create the Problem
        ProblemDTO problemDTO = problemMapper.toDto(problem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProblemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, problemDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(problemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Problem in the database
        List<Problem> problemList = problemRepository.findAll();
        assertThat(problemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProblem() throws Exception {
        int databaseSizeBeforeUpdate = problemRepository.findAll().size();
        problem.setId(count.incrementAndGet());

        // Create the Problem
        ProblemDTO problemDTO = problemMapper.toDto(problem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProblemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(problemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Problem in the database
        List<Problem> problemList = problemRepository.findAll();
        assertThat(problemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProblem() throws Exception {
        int databaseSizeBeforeUpdate = problemRepository.findAll().size();
        problem.setId(count.incrementAndGet());

        // Create the Problem
        ProblemDTO problemDTO = problemMapper.toDto(problem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProblemMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(problemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Problem in the database
        List<Problem> problemList = problemRepository.findAll();
        assertThat(problemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProblem() throws Exception {
        // Initialize the database
        problemRepository.saveAndFlush(problem);

        int databaseSizeBeforeDelete = problemRepository.findAll().size();

        // Delete the problem
        restProblemMockMvc
            .perform(delete(ENTITY_API_URL_ID, problem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Problem> problemList = problemRepository.findAll();
        assertThat(problemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
