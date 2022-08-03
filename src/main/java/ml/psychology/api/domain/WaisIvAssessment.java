package ml.psychology.api.domain;

import lombok.Data;
import ml.psychology.api.domain.enumeration.WaisIvSubTestStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@Entity
public class WaisIvAssessment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private Instant createdDate = Instant.now();

    @NotNull
    private WaisIvSubTestStatus pictureReasoningStatus = WaisIvSubTestStatus.None;

    private Instant pictureReasoningRequestedDate;

    @NotNull
    private String userId;
}
