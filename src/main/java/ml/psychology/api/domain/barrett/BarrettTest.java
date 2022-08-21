package ml.psychology.api.domain.barrett;

import lombok.Data;
import ml.psychology.api.domain.barrett.subtest.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

/**
 * James Barrett Test:
 * Aptitudes Test: These are designed to give you information
 * about types of intelligence that are relevant to different
 * skills and careers. Completing all the tests will allow
 * you to establish your likely strengths and weaknesses in
 * order that you can: 1) know what your strengths are and
 * find a way to use them; 2) know your weaknesses, at the
 * same time not allowing yourself to be limited by them.
 */
@Data
@Entity
public class BarrettTest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private Instant createdDate = Instant.now();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "createdDate", column = @Column (name = "vrCreatedDate")),
            @AttributeOverride(name = "completedDate", column = @Column (name = "vrCompletedDate"))
    })
    private VisualReasoningSubtest visualReasoningSubtest = new VisualReasoningSubtest();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "createdDate", column = @Column (name = "nrCreatedDate")),
            @AttributeOverride(name = "completedDate", column = @Column (name = "nrCompletedDate"))
    })
    private NumericalReasoningSubtest numericalReasoningSubtest = new NumericalReasoningSubtest();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "createdDate", column = @Column (name = "vaCreatedDate")),
            @AttributeOverride(name = "completedDate", column = @Column (name = "vaCompletedDate"))
    })
    private VerbalAnalysisSubtest verbalAnalysisSubTest = new VerbalAnalysisSubtest();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "createdDate", column = @Column (name = "srCreatedDate")),
            @AttributeOverride(name = "completedDate", column = @Column (name = "srCompletedDate"))
    })
    private SequentialReasoningSubtest sequentialReasoningSubtest = new SequentialReasoningSubtest();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "createdDate", column = @Column (name = "sprCreatedDate")),
            @AttributeOverride(name = "completedDate", column = @Column (name = "sprCompletedDate"))
    })
    private SpatialRecognitionSubtest spatialRecognitionSubtest = new SpatialRecognitionSubtest();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "createdDate", column = @Column (name = "tdCreatedDate")),
            @AttributeOverride(name = "completedDate", column = @Column (name = "tdCompletedDate"))
    })
    private ThreeDSubtest threeDSubtest = new ThreeDSubtest();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "createdDate", column = @Column (name = "sCreatedDate")),
            @AttributeOverride(name = "completedDate", column = @Column (name = "sCompletedDate"))
    })
    private SystemsSubtest systemsSubtest = new SystemsSubtest();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "createdDate", column = @Column (name = "vCreatedDate")),
            @AttributeOverride(name = "completedDate", column = @Column (name = "vCompletedDate"))
    })
    private VocabularySubtest vocabularySubtest = new VocabularySubtest();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "createdDate", column = @Column (name = "fwCreatedDate")),
            @AttributeOverride(name = "completedDate", column = @Column (name = "fwCompletedDate"))
    })
    private FigureWorkSubtest figureWorkSubtest = new FigureWorkSubtest();

    @NotNull
    private String userId;
}
