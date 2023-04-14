package ee.cyber.manatee.model;

import java.time.OffsetDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import ee.cyber.manatee.statemachine.InterviewType;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Interview {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private OffsetDateTime scheduledDateTime;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    private Interviewer interviewer;

    @NotNull
    private InterviewType type;
}
