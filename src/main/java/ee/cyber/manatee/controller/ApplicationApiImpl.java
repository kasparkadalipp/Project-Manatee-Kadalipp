package ee.cyber.manatee.controller;


import java.time.OffsetDateTime;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ee.cyber.manatee.api.ApplicationApi;
import ee.cyber.manatee.dto.ApplicationDto;
import ee.cyber.manatee.mapper.ApplicationMapper;
import ee.cyber.manatee.service.ApplicationService;
import ee.cyber.manatee.dto.InterviewDto;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;


@Slf4j
@RestController
@RequiredArgsConstructor
public class ApplicationApiImpl implements ApplicationApi {

    private final ApplicationMapper applicationMapper;
    private final ApplicationService applicationService;

    @Override
    public ResponseEntity<List<ApplicationDto>> getApplications() {
        val applications = applicationService.getApplications();
        return ResponseEntity.ok(applicationMapper.entitiesToDtoList(applications));
    }

    @Override
    public ResponseEntity<ApplicationDto> addApplication(ApplicationDto applicationDto) {
        val draftApplication = applicationMapper.dtoToEntity(applicationDto);
        val application = applicationService.insertApplication(draftApplication);

        return ResponseEntity.status(CREATED)
                             .body(applicationMapper.entityToDto(application));
    }

    @Override
    public ResponseEntity<Void> scheduleInterview(Integer applicationId, InterviewDto interviewDto) {
        if(interviewDto.getScheduledDateTime().isBefore(OffsetDateTime.now())){
            log.error("Scheduled date can't be in the past {}", interviewDto.getScheduledDateTime());
            throw new ResponseStatusException(BAD_REQUEST, "Scheduled date can't be in the past");
        }
        try {
            applicationService.scheduleInterview(applicationId, applicationMapper.dtoToEntity(interviewDto));
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(NOT_FOUND, "Invalid application id", exception);
        }
    }
    @Override
    public ResponseEntity<Void> rejectApplication(Integer applicationId) {
        try {
            applicationService.rejectApplication(applicationId);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(NOT_FOUND, "Invalid application id", exception);
        }
    }
}
