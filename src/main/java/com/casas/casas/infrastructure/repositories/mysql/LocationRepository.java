    package com.casas.casas.infrastructure.repositories.mysql;

    import com.casas.casas.infrastructure.entities.LocationEntity;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.jpa.repository.JpaRepository;

    public interface LocationRepository extends JpaRepository<LocationEntity, Long> {
        Page<LocationEntity> findByCityId(Long idCity, Pageable pageable);
    }