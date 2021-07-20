package com.ustudy.requestservice.repositories;

import com.ustudy.requestservice.models.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuggestionRepository extends JpaRepository<Suggestion,Long> {

}
