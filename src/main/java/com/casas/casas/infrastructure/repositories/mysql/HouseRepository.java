package com.casas.casas.infrastructure.repositories.mysql;

import com.casas.casas.infrastructure.entities.HouseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;


public interface HouseRepository extends JpaRepository<HouseEntity, Long> {
    @Query("SELECT h FROM HouseEntity h WHERE (:idLocation IS NULL OR h.location.id = :idLocation) " +
            "AND (:idCategory IS NULL OR h.category.id = :idCategory) " +
            "AND (:numberOfRooms IS NULL OR h.numberOfRooms = :numberOfRooms) " +
            "AND (:numberOfBathrooms IS NULL OR h.numberOfBathrooms = :numberOfBathrooms) " +
            "AND (:minPrice IS NULL OR h.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR h.price <= :maxPrice) " +
            "AND h.publishActivationDate <= :currentDate")
    Page<HouseEntity> findWithFilters(@Param("idLocation") Long idLocation,
                                      @Param("idCategory") Long idCategory,
                                      @Param("numberOfRooms") Integer numberOfRooms,
                                      @Param("numberOfBathrooms") Integer numberOfBathrooms,
                                      @Param("minPrice") Integer minPrice,
                                      @Param("maxPrice") Integer maxPrice,
                                      @Param("currentDate") LocalDateTime currentDate,
                                      Pageable pageable);
    @Query("SELECT h FROM HouseEntity h WHERE h.publishActivationDate <= :currentDate")
    Page<HouseEntity> findAllActive(@Param("currentDate") LocalDateTime currentDate, Pageable pageable);
    boolean existsByAddress(String address);

}
