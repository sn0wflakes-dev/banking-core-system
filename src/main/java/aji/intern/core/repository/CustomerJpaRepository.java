package aji.intern.core.repository;

import aji.intern.core.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, String> {
    Optional<CustomerEntity> findByCif(String cif);
}
