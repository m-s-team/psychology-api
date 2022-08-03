package ml.psychology.api.service;

import ml.psychology.api.domain.WaisIvAssessment;
import ml.psychology.api.repository.WaisIvAssessmentRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class WaisIvAssessmentService {

    private final WaisIvAssessmentRepository waisIvAssessmentRepository;

    public WaisIvAssessmentService(WaisIvAssessmentRepository waisIvAssessmentRepository) {
        this.waisIvAssessmentRepository = waisIvAssessmentRepository;
    }

    public WaisIvAssessment createForUser(String userId) {
        WaisIvAssessment waisIvAssessment = new WaisIvAssessment();
        waisIvAssessment.setUserId(userId);
        return waisIvAssessmentRepository.save(waisIvAssessment);
    }

    public Optional<WaisIvAssessment> getById(Long id) {
        return waisIvAssessmentRepository.findById(id);
    }

    public List<WaisIvAssessment> getByUserId(String userId) {
        return waisIvAssessmentRepository.findByUserId(userId);
    }

    public WaisIvAssessment update(WaisIvAssessment assessment) {
        return waisIvAssessmentRepository.save(assessment);
    }

    public void deleteById(Long id) {
        waisIvAssessmentRepository.deleteById(id);
    }

    public boolean isOwner(Jwt principal, Long id) {
        String userId = principal.getSubject();
        return waisIvAssessmentRepository.findById(id)
                .map(assessment -> assessment.getUserId().equals(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
