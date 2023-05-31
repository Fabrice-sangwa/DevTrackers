package com.esisalama.service;

import com.esisalama.domain.Team;
import com.esisalama.repository.TeamRepository;
import com.esisalama.service.dto.TeamDTO;
import com.esisalama.service.mapper.TeamMapper;
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
 * Service Implementation for managing {@link Team}.
 */
@Service
@Transactional
public class TeamService {

    private final Logger log = LoggerFactory.getLogger(TeamService.class);

    private final TeamRepository teamRepository;

    private final TeamMapper teamMapper;

    public TeamService(TeamRepository teamRepository, TeamMapper teamMapper) {
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
    }

    /**
     * Save a team.
     *
     * @param teamDTO the entity to save.
     * @return the persisted entity.
     */
    public TeamDTO save(TeamDTO teamDTO) {
        log.debug("Request to save Team : {}", teamDTO);
        Team team = teamMapper.toEntity(teamDTO);
        team = teamRepository.save(team);
        return teamMapper.toDto(team);
    }

    /**
     * Update a team.
     *
     * @param teamDTO the entity to save.
     * @return the persisted entity.
     */
    public TeamDTO update(TeamDTO teamDTO) {
        log.debug("Request to update Team : {}", teamDTO);
        Team team = teamMapper.toEntity(teamDTO);
        team = teamRepository.save(team);
        return teamMapper.toDto(team);
    }

    /**
     * Partially update a team.
     *
     * @param teamDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TeamDTO> partialUpdate(TeamDTO teamDTO) {
        log.debug("Request to partially update Team : {}", teamDTO);

        return teamRepository
            .findById(teamDTO.getId())
            .map(existingTeam -> {
                teamMapper.partialUpdate(existingTeam, teamDTO);

                return existingTeam;
            })
            .map(teamRepository::save)
            .map(teamMapper::toDto);
    }

    /**
     * Get all the teams.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TeamDTO> findAll() {
        log.debug("Request to get all Teams");
        return teamRepository.findAll().stream().map(teamMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the teams with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<TeamDTO> findAllWithEagerRelationships(Pageable pageable) {
        return teamRepository.findAllWithEagerRelationships(pageable).map(teamMapper::toDto);
    }

    /**
     * Get one team by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TeamDTO> findOne(Long id) {
        log.debug("Request to get Team : {}", id);
        return teamRepository.findOneWithEagerRelationships(id).map(teamMapper::toDto);
    }

    /**
     * Delete the team by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Team : {}", id);
        teamRepository.deleteById(id);
    }
}
