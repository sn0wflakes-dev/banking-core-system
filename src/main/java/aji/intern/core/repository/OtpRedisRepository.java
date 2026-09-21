package aji.intern.core.repository;

import aji.intern.core.entity.OtpEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OtpRedisRepository extends CrudRepository<OtpEntity, String> {

    @Override
    @NonNull
    Optional<OtpEntity> findById(@NonNull String email);
}
