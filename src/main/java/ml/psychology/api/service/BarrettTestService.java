package ml.psychology.api.service;

import ml.psychology.api.domain.barrett.BarrettTest;
import ml.psychology.api.repository.barret.BarrettTestRepository;
import ml.psychology.api.service.dto.barrett.BarrettTestDTO;
import ml.psychology.api.service.mapper.barrett.BarrettTestMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BarrettTestService {

    private final BarrettTestRepository barrettTestRepository;

    private final BarrettTestMapper barrettTestMapper;

    public BarrettTestService(BarrettTestRepository barrettTestRepository, BarrettTestMapper barrettTestMapper) {
        this.barrettTestRepository = barrettTestRepository;
        this.barrettTestMapper = barrettTestMapper;
    }

    public BarrettTestDTO createForUser(String userId) {
        BarrettTest barrettTest = new BarrettTest();
        barrettTest.setUserId(userId);
        BarrettTest r = barrettTestRepository.save(barrettTest);
        System.out.println(r);
        return barrettTestMapper.entityToDto(r);
    }

    public List<BarrettTestDTO> getByUserId(String userId) {
        return barrettTestRepository
                .findByUserId(userId)
                .stream()
                .map(barrettTestMapper::entityToDto)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        barrettTestRepository.deleteById(id);
    }

    public boolean isOwner(Jwt principal, Long id) {
        String userId = principal.getSubject();
        return barrettTestRepository.findById(id)
                .map(assessment -> assessment.getUserId().equals(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
