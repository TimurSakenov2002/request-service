package com.ustudy.requestservice.services;

import com.ustudy.requestservice.models.Suggestion;
import com.ustudy.requestservice.repositories.SuggestionRepository;
import com.ustudy.requestservice.services.interfaces.EntityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SuggestionService implements EntityService<Suggestion, Long> {
    private final SuggestionRepository suggestionRepository;
    @Override
    public Suggestion save(Suggestion entity) {
        return suggestionRepository.save(entity);
    }

    @Override
    public Suggestion update(Suggestion entity) {
        return save(entity);
    }

    @Override
    public void delete(Suggestion entity) {
        suggestionRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        suggestionRepository.deleteById(id);
    }

    @Override
    public Suggestion getById(Long id) throws Exception {
        return suggestionRepository.findById(id).get();
    }
}
