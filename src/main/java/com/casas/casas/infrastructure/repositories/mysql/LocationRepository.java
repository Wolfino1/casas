    package com.casas.casas.infrastructure.repositories.mysql;

    import com.casas.casas.infrastructure.entities.LocationEntity;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.data.repository.query.Param;

    import java.util.List;
    import java.util.Optional;

    public interface LocationRepository extends JpaRepository<LocationEntity, Long> {
        Page<LocationEntity> findByCityId(Long idCity, Pageable pageable);
        Optional<LocationEntity> findByName(String name);

        @Query("SELECT l FROM LocationEntity l WHERE l.city.name = :cityName")
        Optional<LocationEntity> findByCityName(@Param("cityName") String cityName);

        @Query("SELECT l FROM LocationEntity l " +
                "JOIN l.city c " +
                "JOIN c.department d " +
                "WHERE LOWER(d.name) = LOWER(:departmentName)")
        //       "WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :departmentName, '%'))")
        List<LocationEntity> findAllByDepartmentName(@Param("departmentName") String departmentName);


        Page<LocationEntity> findByCity_Department_Id(Long idDepartment, Pageable pageable);

        @Query("""
    SELECT l FROM LocationEntity l
    JOIN l.city c
    JOIN c.department d
    WHERE LOWER(l.name) LIKE LOWER(CONCAT('%', :text, '%'))
       OR LOWER(c.name) LIKE LOWER(CONCAT('%', :text, '%'))
       OR LOWER(d.name) LIKE LOWER(CONCAT('%', :text, '%'))
""")
        Page<LocationEntity> searchByText(@Param("text") String text, Pageable pageable);
    }