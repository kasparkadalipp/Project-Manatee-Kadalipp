package ee.cyber.manatee.data;


import java.time.OffsetDateTime;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ee.cyber.manatee.model.Application;
import ee.cyber.manatee.model.Candidate;
import ee.cyber.manatee.model.Interview;
import ee.cyber.manatee.repository.ApplicationRepository;
import ee.cyber.manatee.statemachine.ApplicationState;


@Slf4j
@Component
public class DataLoader {

    private final ApplicationRepository applicationRepository;

    @Autowired
    public DataLoader(ApplicationRepository userRepository) {
        this.applicationRepository = userRepository;
        loadApplications();
    }

    private void loadApplications() {
        Stream.of(
            new String[]{"NEW", "John", "Doe", "2023-04-14T09:00:00Z", null},
            new String[]{"NEW", "Jane", "Smith", "2023-04-14T10:00:00Z", null},
            new String[]{"NEW", "Mark", "Johnson", "2023-04-14T11:00:00Z", null},
            new String[]{"INTERVIEW", "Sarah", "Lee", "2023-04-14T12:00:00Z", "2023-04-18T12:00:00Z"},
            new String[]{"INTERVIEW", "Michael", "Garcia", "2023-04-14T13:00:00Z", "2023-04-22T14:00:00Z"},
            new String[]{"REJECTED", "Emily", "Kim", "2023-04-13T14:00:00Z", "2023-04-14T14:00:00Z",}
        ).map(values -> Application.builder()
                .applicationState(ApplicationState.valueOf(values[0]))
                .candidate(Candidate.builder().firstName(values[1]).lastName(values[2]).build())
                .updatedOn(OffsetDateTime.parse(values[3]))
                .scheduledInterview(values[4] == null ? null :
                        Interview.builder().scheduledDateTime(OffsetDateTime.parse(values[3])).build())
                .build())
        .forEach(applicationRepository::save);

    }
}
