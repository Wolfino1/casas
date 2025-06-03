package com.casas.casas.infrastructure.adapters.persistence.mysql;

import com.casas.casas.domain.exceptions.InvalidPaginationException;
import com.casas.casas.domain.model.HouseModel;
import com.casas.casas.domain.ports.out.HousePersistencePort;
import com.casas.casas.domain.utils.constants.DomainConstants;
import com.casas.casas.domain.utils.page.PagedResult;
import com.casas.casas.infrastructure.entities.HouseEntity;
import com.casas.casas.infrastructure.mappers.HouseEntityMapper;
import com.casas.casas.infrastructure.mappers.PageMapperInfra;
import com.casas.casas.infrastructure.repositories.mysql.HouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class HousePersistenceAdapter implements HousePersistencePort {
    private final HouseRepository houseRepository;
    private final HouseEntityMapper houseEntityMapper;
    private final PageMapperInfra pageMapperInfra;

    @Override
    public void save(HouseModel houseModel) {
            HouseEntity houseEntity = houseEntityMapper.modelToEntity(houseModel);
            houseRepository.save(houseEntity);

    }

    @Override
    public PagedResult<HouseModel> getFilters(Integer page, Integer size, Long idLocation, Long idCategory, String name,Integer numberOfRooms,
                                              Integer numberOfBathrooms, Integer minPrice, Integer maxPrice, String sortBy, boolean orderAsc) {

        if (page < 0) {
            throw new InvalidPaginationException(DomainConstants.PAGE_NUMBER_INVALID);
        }

        if (size <= 0) {
            throw new InvalidPaginationException(DomainConstants.PAGE_SIZE_INVALID);
        }
        Pageable pagination = orderAsc
                ? PageRequest.of(page, size, Sort.by(sortBy).ascending())
                : PageRequest.of(page, size, Sort.by(sortBy).descending());


        Page<HouseEntity> pageResult;

        if (idLocation != null || idCategory != null || numberOfRooms != null || numberOfBathrooms != null || minPrice != null || maxPrice != null) {
            pageResult = houseRepository.findWithFilters(idLocation, idCategory, name, numberOfRooms,
                    numberOfBathrooms, minPrice, maxPrice, LocalDateTime.now(), pagination);}
        else {
            pageResult = houseRepository.findAllActive(LocalDateTime.now(), pagination);
        }

        return pageMapperInfra.fromPage(pageResult.map(houseEntityMapper::entityToModel));
    }

    @Override
    public boolean existsByAddress(String address) {
        return houseRepository.existsByAddress(address);
    }

    @Override
    public HouseModel getById(Long id) {
        return houseRepository.findById(id)
                .map(houseEntityMapper::entityToModel)
                .orElse(null);
    }

    @Override
    public PagedResult<HouseModel> getFiltersBySeller(Long sellerId, Integer page, Integer size, Long id, String name, Long idLocation, Long idCategory,Integer minPrice, boolean orderAsc) {

        if (page < 0) throw new InvalidPaginationException(DomainConstants.PAGE_NUMBER_INVALID);
        if (size <= 0) throw new InvalidPaginationException(DomainConstants.PAGE_SIZE_INVALID);

        Pageable pagination = orderAsc
                ? PageRequest.of(page, size, Sort.by("id").ascending())
                : PageRequest.of(page, size, Sort.by("id").descending());


        Page<HouseEntity> pageResult;

        if (id != null || idLocation != null || idCategory != null || minPrice != null || name != null) {
            pageResult = houseRepository.findWithFiltersBySeller(
                    sellerId, id, idLocation, idCategory, minPrice, name, LocalDateTime.now(), pagination
            );
        } else {
            pageResult = houseRepository.findAllActiveBySeller(sellerId, LocalDateTime.now(), pagination);
        }


        return pageMapperInfra.fromPage(pageResult.map(houseEntityMapper::entityToModel));
    }

}

