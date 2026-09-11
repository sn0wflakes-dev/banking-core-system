package aji.intern.core.repository;

import aji.intern.core.entity.KeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KeyJpaRepository extends JpaRepository<KeyEntity, String> {
    Optional<KeyEntity> findByServiceId(String serviceId);
}
