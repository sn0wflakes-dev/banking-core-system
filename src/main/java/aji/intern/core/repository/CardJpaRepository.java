package aji.intern.core.repository;

import aji.intern.core.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardJpaRepository extends JpaRepository<CardEntity, String > {
    Optional<CardEntity> findByPan(String pan);
}
