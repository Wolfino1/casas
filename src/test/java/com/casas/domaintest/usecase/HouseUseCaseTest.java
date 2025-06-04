package com.casas.domaintest.usecase;

import com.casas.casas.domain.exceptions.EmptyException;
import com.casas.casas.domain.exceptions.ListingDateExceedTimeException;
import com.casas.casas.domain.model.CategoryModel;
import com.casas.casas.domain.model.HouseModel;
import com.casas.casas.domain.model.LocationModel;
import com.casas.casas.domain.ports.out.HousePersistencePort;
import com.casas.casas.domain.usecases.HouseUseCase;
import com.casas.casas.domain.usecases.CategoryUseCase;
import com.casas.casas.domain.usecases.LocationUseCase;
import com.casas.casas.domain.utils.constants.DomainConstants;
import com.casas.casas.domain.utils.page.PagedResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HouseUseCaseTest {

    @Mock
    private HousePersistencePort housePersistencePort;

    @Mock
    private LocationUseCase locationUseCase;

    @Mock
    private CategoryUseCase categoryUseCase;

    @InjectMocks
    private HouseUseCase houseUseCase;

    @Captor
    private ArgumentCaptor<HouseModel> houseCaptor;

    private HouseModel baseModel;

    @BeforeEach
    void setUp() {
        baseModel = new HouseModel(
                1L,         // id
                2L,         // sellerId
                "Casa X",   // name
                "Calle 1",  // address
                "Desc",     // description
                1L,         // idCategory
                2,          // numberOfRooms
                1,          // numberOfBathrooms
                100000,     // price
                3L,         // idLocation
                LocalDate.now(), // publishActivationDate (valid)
                null,       // idPubStatus
                LocalDateTime.now() // publishDate
        );
    }

    @Test
    void save_ThrowsEmptyException_WhenCategoryDoesNotExist() {
        when(categoryUseCase.getById(1L)).thenReturn(null);
        when(locationUseCase.getById(3L)).thenReturn(new LocationModel(3L, "Loc", 1L, null));

        assertThrows(
                EmptyException.class,
                () -> houseUseCase.save(baseModel)
        );
        verify(categoryUseCase).getById(1L);
        verify(locationUseCase).getById(3L);
        verify(housePersistencePort, never()).save(any());
    }

    @Test
    void save_ThrowsEmptyException_WhenLocationDoesNotExist() {
        when(categoryUseCase.getById(1L)).thenReturn(new CategoryModel(1L, "Cat", "Desc"));
        when(locationUseCase.getById(3L)).thenReturn(null);

        assertThrows(
                EmptyException.class,
                () -> houseUseCase.save(baseModel)
        );
        verify(categoryUseCase).getById(1L);
        verify(locationUseCase).getById(3L);
        verify(housePersistencePort, never()).save(any());
    }

    @Test
    void save_ThrowsIllegalArgumentException_WhenAddressAlreadyExists() {
        when(categoryUseCase.getById(1L)).thenReturn(new CategoryModel(1L, "Cat", "Desc"));
        when(locationUseCase.getById(3L)).thenReturn(new LocationModel(3L, "Loc", 1L, null));
        when(housePersistencePort.existsByAddress("Calle 1")).thenReturn(true);

        assertThrows(
                IllegalArgumentException.class,
                () -> houseUseCase.save(baseModel)
        );
        verify(housePersistencePort).existsByAddress("Calle 1");
        verify(housePersistencePort, never()).save(any());
    }

    @Test
    void save_ThrowsListingDateExceedTimeException_WhenPublishDateExceedsLimit() throws Exception {
        when(categoryUseCase.getById(1L)).thenReturn(new CategoryModel(1L, "Cat", "Desc"));
        when(locationUseCase.getById(3L)).thenReturn(new LocationModel(3L, "Loc", 1L, null));
        when(housePersistencePort.existsByAddress("Calle 1")).thenReturn(false);

        LocalDate limit = LocalDate.now().plusMonths(DomainConstants.MONTHS_TO_ADD);
        LocalDate outOfRange = limit.plusDays(1);

        // Bypass setter validation via reflection
        Field field = HouseModel.class.getDeclaredField("publishActivationDate");
        field.setAccessible(true);
        field.set(baseModel, outOfRange);

        assertThrows(
                ListingDateExceedTimeException.class,
                () -> houseUseCase.save(baseModel)
        );
        verify(housePersistencePort, never()).save(any());
    }

    @Test
    void save_SetsIdPubStatusActive_WhenPublishDateInPast() {
        when(categoryUseCase.getById(1L)).thenReturn(new CategoryModel(1L, "Cat", "Desc"));
        when(locationUseCase.getById(3L)).thenReturn(new LocationModel(3L, "Loc", 1L, null));
        when(housePersistencePort.existsByAddress("Calle 1")).thenReturn(false);

        // set a past date via setter
        baseModel.setPublishActivationDate(LocalDate.now().minusDays(1));

        houseUseCase.save(baseModel);

        verify(housePersistencePort).save(houseCaptor.capture());
        HouseModel saved = houseCaptor.getValue();
        assertEquals(DomainConstants.PUB_STATUS_IS_ACTIVE, saved.getIdPubStatus());
    }

    @Test
    void save_SetsIdPubStatusDraft_WhenPublishDateToday() {
        when(categoryUseCase.getById(1L)).thenReturn(new CategoryModel(1L, "Cat", "Desc"));
        when(locationUseCase.getById(3L)).thenReturn(new LocationModel(3L, "Loc", 1L, null));
        when(housePersistencePort.existsByAddress("Calle 1")).thenReturn(false);

        // publishActivationDate is already today
        houseUseCase.save(baseModel);

        verify(housePersistencePort).save(houseCaptor.capture());
        HouseModel saved = houseCaptor.getValue();
        assertEquals(DomainConstants.PUB_STATUS_IS_DRAFT, saved.getIdPubStatus());
    }

    @Test
    void save_SetsIdPubStatusDraft_WhenPublishDateIsNull() {
        when(categoryUseCase.getById(1L)).thenReturn(new CategoryModel(1L, "Cat", "Desc"));
        when(locationUseCase.getById(3L)).thenReturn(new LocationModel(3L, "Loc", 1L, null));
        when(housePersistencePort.existsByAddress("Calle 1")).thenReturn(false);

        // Bypass setter by setting field directly to null
        baseModel.setPublishActivationDate(LocalDate.now()); // first set valid
        try {
            Field field = HouseModel.class.getDeclaredField("publishActivationDate");
            field.setAccessible(true);
            field.set(baseModel, null);
        } catch (Exception e) {
            fail("Reflection error: " + e.getMessage());
        }

        houseUseCase.save(baseModel);

        verify(housePersistencePort).save(houseCaptor.capture());
        HouseModel saved = houseCaptor.getValue();
        assertEquals(DomainConstants.PUB_STATUS_IS_DRAFT, saved.getIdPubStatus());
    }

    @Test
    void getById_ReturnsHouseModel_WhenExists() {
        HouseModel example = new HouseModel(
                5L, 2L, "Casa", "Dir", "Desc",
                1L, 2, 1, 200000, 3L,
                LocalDate.now(), 1L, LocalDateTime.now()
        );
        when(housePersistencePort.getById(5L)).thenReturn(example);

        HouseModel result = houseUseCase.getById(5L);

        assertSame(example, result);
    }

    @Test
    void getById_ThrowsEmptyException_WhenNotFound() {
        when(housePersistencePort.getById(9L)).thenReturn(null);

        assertThrows(
                EmptyException.class,
                () -> houseUseCase.getById(9L)
        );
    }

    @Test
    void getFilters_DelegatesToPersistencePort() {
        PagedResult<HouseModel> expected = new PagedResult<>(Collections.emptyList(), 0, 10, 1L);
        when(housePersistencePort.getFilters(
                eq(0), eq(10), eq(3L), eq(1L),
                eq("name"), eq(2), eq(1), eq(100), eq(200),
                eq("price"), eq(true))
        ).thenReturn(expected);

        PagedResult<HouseModel> result = houseUseCase.getFilters(
                0, 10, 3L, 1L, "name", 2, 1, 100, 200, "price", true
        );

        assertSame(expected, result);
    }

    @Test
    void getFiltersBySeller_DelegatesToPersistencePort() {
        PagedResult<HouseModel> expected = new PagedResult<>(Collections.emptyList(), 0, 10, 1L);
        when(housePersistencePort.getFiltersBySeller(
                eq(2L), eq(0), eq(10), eq(5L),
                eq("n"), eq(3L), eq(1L), eq(100), eq(false))
        ).thenReturn(expected);

        PagedResult<HouseModel> result = houseUseCase.getFiltersBySeller(
                2L, 0, 10, 5L, "n", 3L, 1L, 100, false
        );

        assertSame(expected, result);
    }
}
