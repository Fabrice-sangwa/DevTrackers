package com.esisalama.web.rest;

import com.esisalama.repository.DashboardRepository;
import com.esisalama.service.DashboardService;
import com.esisalama.service.dto.DashboardDTO;
import com.esisalama.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.esisalama.domain.Dashboard}.
 */
@RestController
@RequestMapping("/api")
public class DashboardResource {

    private final Logger log = LoggerFactory.getLogger(DashboardResource.class);

    private static final String ENTITY_NAME = "dashboard";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DashboardService dashboardService;

    private final DashboardRepository dashboardRepository;

    public DashboardResource(DashboardService dashboardService, DashboardRepository dashboardRepository) {
        this.dashboardService = dashboardService;
        this.dashboardRepository = dashboardRepository;
    }

    /**
     * {@code POST  /dashboards} : Create a new dashboard.
     *
     * @param dashboardDTO the dashboardDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dashboardDTO, or with status {@code 400 (Bad Request)} if the dashboard has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dashboards")
    public ResponseEntity<DashboardDTO> createDashboard(@RequestBody DashboardDTO dashboardDTO) throws URISyntaxException {
        log.debug("REST request to save Dashboard : {}", dashboardDTO);
        if (dashboardDTO.getId() != null) {
            throw new BadRequestAlertException("A new dashboard cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DashboardDTO result = dashboardService.save(dashboardDTO);
        return ResponseEntity
            .created(new URI("/api/dashboards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dashboards/:id} : Updates an existing dashboard.
     *
     * @param id the id of the dashboardDTO to save.
     * @param dashboardDTO the dashboardDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dashboardDTO,
     * or with status {@code 400 (Bad Request)} if the dashboardDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dashboardDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dashboards/{id}")
    public ResponseEntity<DashboardDTO> updateDashboard(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DashboardDTO dashboardDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Dashboard : {}, {}", id, dashboardDTO);
        if (dashboardDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dashboardDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dashboardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DashboardDTO result = dashboardService.update(dashboardDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dashboardDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /dashboards/:id} : Partial updates given fields of an existing dashboard, field will ignore if it is null
     *
     * @param id the id of the dashboardDTO to save.
     * @param dashboardDTO the dashboardDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dashboardDTO,
     * or with status {@code 400 (Bad Request)} if the dashboardDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dashboardDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dashboardDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/dashboards/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DashboardDTO> partialUpdateDashboard(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DashboardDTO dashboardDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Dashboard partially : {}, {}", id, dashboardDTO);
        if (dashboardDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dashboardDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dashboardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DashboardDTO> result = dashboardService.partialUpdate(dashboardDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dashboardDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /dashboards} : get all the dashboards.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dashboards in body.
     */
    @GetMapping("/dashboards")
    public List<DashboardDTO> getAllDashboards() {
        log.debug("REST request to get all Dashboards");
        return dashboardService.findAll();
    }

    /**
     * {@code GET  /dashboards/:id} : get the "id" dashboard.
     *
     * @param id the id of the dashboardDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dashboardDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dashboards/{id}")
    public ResponseEntity<DashboardDTO> getDashboard(@PathVariable Long id) {
        log.debug("REST request to get Dashboard : {}", id);
        Optional<DashboardDTO> dashboardDTO = dashboardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dashboardDTO);
    }

    /**
     * {@code DELETE  /dashboards/:id} : delete the "id" dashboard.
     *
     * @param id the id of the dashboardDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dashboards/{id}")
    public ResponseEntity<Void> deleteDashboard(@PathVariable Long id) {
        log.debug("REST request to delete Dashboard : {}", id);
        dashboardService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
