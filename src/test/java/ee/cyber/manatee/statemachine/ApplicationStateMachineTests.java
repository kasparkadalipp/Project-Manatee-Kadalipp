package ee.cyber.manatee.statemachine;


import javax.transaction.Transactional;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ee.cyber.manatee.model.Application;
import ee.cyber.manatee.model.Candidate;
import ee.cyber.manatee.repository.ApplicationRepository;
import ee.cyber.manatee.service.ApplicationService;

import static ee.cyber.manatee.statemachine.ApplicationState.INTERVIEW;
import static ee.cyber.manatee.statemachine.ApplicationState.REJECTED;
import static ee.cyber.manatee.statemachine.ApplicationState.NEW;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


@SpringBootTest
class ApplicationStateMachineTests {

    @Autowired
    ApplicationService applicationService;

    @Autowired
    ApplicationStateMachine applicationStateMachine;

    @Autowired
    ApplicationRepository applicationRepository;

    private static final Candidate newCandidate = Candidate.builder().firstName("Mari").lastName("Maasikas").build();
    private static final Application newApplication = Application.builder().candidate(newCandidate).build();

    @Test
    @Transactional
    void applicationGetsRejected() {
        val applicationSaved = applicationService.insertApplication(newApplication);
        val initialUpdatedOn = applicationSaved.getUpdatedOn();
        assertEquals(NEW, applicationSaved.getApplicationState());

        val stateMachine = applicationStateMachine.rejectApplication(applicationSaved.getId());
        assertEquals(REJECTED, stateMachine.getState().getId());

        val optionalRejectedApplication = applicationRepository.findById(applicationSaved.getId());
        assertFalse(optionalRejectedApplication.isEmpty());

        val rejectedApplication = optionalRejectedApplication.get();
        assertEquals(REJECTED, rejectedApplication.getApplicationState());
        assertNotEquals(initialUpdatedOn, rejectedApplication.getUpdatedOn());

    }

    @Test
    @Transactional
    void applicationInterviewGetsScheduled() {
        val applicationSaved = applicationService.insertApplication(newApplication);
        val initialUpdatedOn = applicationSaved.getUpdatedOn();
        assertEquals(NEW, applicationSaved.getApplicationState());

        val stateMachine = applicationStateMachine.scheduleInterview(applicationSaved.getId());
        assertEquals(INTERVIEW, stateMachine.getState().getId());

        val optionalScheduledApplication = applicationRepository.findById(applicationSaved.getId());
        assertFalse(optionalScheduledApplication.isEmpty());

        val scheduledApplication = optionalScheduledApplication.get();
        assertEquals(INTERVIEW, scheduledApplication.getApplicationState());
        assertNotEquals(initialUpdatedOn, scheduledApplication.getUpdatedOn());
    }
}
