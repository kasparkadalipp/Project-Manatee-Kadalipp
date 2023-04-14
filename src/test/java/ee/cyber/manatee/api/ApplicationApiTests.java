package ee.cyber.manatee.api;


import ee.cyber.manatee.model.Application;
import ee.cyber.manatee.model.Candidate;
import ee.cyber.manatee.repository.ApplicationRepository;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import ee.cyber.manatee.dto.ApplicationDto;
import ee.cyber.manatee.dto.CandidateDto;
import ee.cyber.manatee.dto.InterviewDto;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@AutoConfigureMockMvc
class ApplicationApiTests {

    @Autowired
    private ApplicationApi applicationApi;

    @Mock
    private ApplicationRepository applicationRepository;

    @Test
    void submitApplicationWithValidData() {
        val draftCandidate = CandidateDto
                .builder().firstName("Mari").lastName("Maasikas").build();
        val draftApplication = ApplicationDto
                .builder().candidate(draftCandidate).build();

        val response = applicationApi.addApplication(draftApplication);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        val application = response.getBody();
        assertNotNull(application);
        assertNotNull(application.getId());
        assertNotNull(application.getApplicationState());
        assertNotNull(application.getUpdatedOn());

        assertEquals(draftApplication.getCandidate().getFirstName(),
                     application.getCandidate().getFirstName());
        assertEquals(draftApplication.getCandidate().getLastName(),
                     application.getCandidate().getLastName());
    }


    @Test
    void scheduleInterviewWithValidData() {
        int applicationId = 1;
        val draftCandidate = Candidate
                .builder().firstName("Mari").lastName("Maasikas").build();
        val draftApplication = Application
                .builder().candidate(draftCandidate).id(applicationId).build();
        val draftInterview = InterviewDto
                .builder().scheduledDateTime(OffsetDateTime.now().plusDays(1)).build();

        Mockito.when(applicationRepository.findById(applicationId)).thenReturn(Optional.ofNullable(draftApplication));
        val response = applicationApi.scheduleInterview(applicationId, draftInterview);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }


    @Test
    void scheduleInterviewWithInvalidData() {
        int applicationId = 1;
        val draftCandidate = Candidate
                .builder().firstName("Mari").lastName("Maasikas").build();
        val draftApplication = Application
                .builder().candidate(draftCandidate).id(applicationId).build();
        val draftInterview = InterviewDto
                .builder().scheduledDateTime(OffsetDateTime.now().minusDays(applicationId)).build();

        Mockito.when(applicationRepository.findById(applicationId)).thenReturn(Optional.ofNullable(draftApplication));
        val response = assertThrows(ResponseStatusException.class, () -> applicationApi.scheduleInterview(applicationId, draftInterview)) ;
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
    }
}
