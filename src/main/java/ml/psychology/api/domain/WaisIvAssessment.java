package ml.psychology.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ml.psychology.api.domain.enumeration.WaisIvSubTestStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "assessment")
    @JsonIgnoreProperties
    private Set<PictureReasoningTestResult> pictureReasoningTestResults = new HashSet<>();

    @NotNull
    private String userId;
}
