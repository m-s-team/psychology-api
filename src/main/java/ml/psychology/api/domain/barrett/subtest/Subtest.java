package ml.psychology.api.domain.barrett.subtest;

import lombok.Data;

import javax.persistence.MappedSuperclass;
import java.time.Instant;

@Data
@MappedSuperclass
public class Subtest {

    private Instant createdDate;
    private Instant completedDate;

}
