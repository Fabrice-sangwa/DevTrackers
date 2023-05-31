package com.esisalama.service;

import com.esisalama.domain.Problem;
import com.esisalama.repository.ProblemRepository;
import com.esisalama.service.dto.ProblemDTO;
import com.esisalama.service.mapper.ProblemMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Problem}.
 */
@Service
@Transactional
public class ProblemService {

    private final Logger log = LoggerFactory.getLogger(ProblemService.class);

    private final ProblemRepository problemRepository;

    private final ProblemMapper problemMapper;

    public ProblemService(ProblemRepository problemRepository, ProblemMapper problemMapper) {
        this.problemRepository = problemRepository;
        this.problemMapper = problemMapper;
    }

    /**
     * Save a problem.
     *
     * @param problemDTO the entity to save.
     * @return the persisted entity.
     */
    public ProblemDTO save(ProblemDTO problemDTO) {
        log.debug("Request to save Problem : {}", problemDTO);
        Problem problem = problemMapper.toEntity(problemDTO);
        problem = problemRepository.save(problem);
        return problemMapper.toDto(problem);
    }

    /**
     * Update a problem.
     *
     * @param problemDTO the entity to save.
     * @return the persisted entity.
     */
    public ProblemDTO update(ProblemDTO problemDTO) {
        log.debug("Request to update Problem : {}", problemDTO);
        Problem problem = problemMapper.toEntity(problemDTO);
        problem = problemRepository.save(problem);
        return problemMapper.toDto(problem);
    }

    /**
     * Partially update a problem.
     *
     * @param problemDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProblemDTO> partialUpdate(ProblemDTO problemDTO) {
        log.debug("Request to partially update Problem : {}", problemDTO);

        return problemRepository
            .findById(problemDTO.getId())
            .map(existingProblem -> {
                problemMapper.partialUpdate(existingProblem, problemDTO);

                return existingProblem;
            })
            .map(problemRepository::save)
            .map(problemMapper::toDto);
    }

    /**
     * Get all the problems.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProblemDTO> findAll() {
        log.debug("Request to get all Problems");
        return problemRepository.findAll().stream().map(problemMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the problems with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ProblemDTO> findAllWithEagerRelationships(Pageable pageable) {
        return problemRepository.findAllWithEagerRelationships(pageable).map(problemMapper::toDto);
    }

    /**
     * Get one problem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProblemDTO> findOne(Long id) {
        log.debug("Request to get Problem : {}", id);
        return problemRepository.findOneWithEagerRelationships(id).map(problemMapper::toDto);
    }

    /**
     * Delete the problem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Problem : {}", id);
        problemRepository.deleteById(id);
    }
}
