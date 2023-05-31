package com.esisalama.service;

import com.esisalama.domain.Dashboard;
import com.esisalama.repository.DashboardRepository;
import com.esisalama.service.dto.DashboardDTO;
import com.esisalama.service.mapper.DashboardMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Dashboard}.
 */
@Service
@Transactional
public class DashboardService {

    private final Logger log = LoggerFactory.getLogger(DashboardService.class);

    private final DashboardRepository dashboardRepository;

    private final DashboardMapper dashboardMapper;

    public DashboardService(DashboardRepository dashboardRepository, DashboardMapper dashboardMapper) {
        this.dashboardRepository = dashboardRepository;
        this.dashboardMapper = dashboardMapper;
    }

    /**
     * Save a dashboard.
     *
     * @param dashboardDTO the entity to save.
     * @return the persisted entity.
     */
    public DashboardDTO save(DashboardDTO dashboardDTO) {
        log.debug("Request to save Dashboard : {}", dashboardDTO);
        Dashboard dashboard = dashboardMapper.toEntity(dashboardDTO);
        dashboard = dashboardRepository.save(dashboard);
        return dashboardMapper.toDto(dashboard);
    }

    /**
     * Update a dashboard.
     *
     * @param dashboardDTO the entity to save.
     * @return the persisted entity.
     */
    public DashboardDTO update(DashboardDTO dashboardDTO) {
        log.debug("Request to update Dashboard : {}", dashboardDTO);
        Dashboard dashboard = dashboardMapper.toEntity(dashboardDTO);
        // no save call needed as we have no fields that can be updated
        return dashboardMapper.toDto(dashboard);
    }

    /**
     * Partially update a dashboard.
     *
     * @param dashboardDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DashboardDTO> partialUpdate(DashboardDTO dashboardDTO) {
        log.debug("Request to partially update Dashboard : {}", dashboardDTO);

        return dashboardRepository
            .findById(dashboardDTO.getId())
            .map(existingDashboard -> {
                dashboardMapper.partialUpdate(existingDashboard, dashboardDTO);

                return existingDashboard;
            })
            // .map(dashboardRepository::save)
            .map(dashboardMapper::toDto);
    }

    /**
     * Get all the dashboards.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DashboardDTO> findAll() {
        log.debug("Request to get all Dashboards");
        return dashboardRepository.findAll().stream().map(dashboardMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one dashboard by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DashboardDTO> findOne(Long id) {
        log.debug("Request to get Dashboard : {}", id);
        return dashboardRepository.findById(id).map(dashboardMapper::toDto);
    }

    /**
     * Delete the dashboard by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Dashboard : {}", id);
        dashboardRepository.deleteById(id);
    }
}
