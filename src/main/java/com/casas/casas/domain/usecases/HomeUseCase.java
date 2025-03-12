package com.casas.casas.domain.usecases;

import com.casas.casas.domain.exceptions.HomeAlreadyExistsException;
import com.casas.casas.domain.model.HomeModel;
import com.casas.casas.domain.ports.in.HomeServicePort;
import com.casas.casas.domain.ports.out.HomePersistencePort;

import java.util.List;

public class HomeUseCase implements HomeServicePort {
    private final HomePersistencePort homePersistencePort;

    public HomeUseCase(HomePersistencePort homePersistencePort) {
        this.homePersistencePort = homePersistencePort;
    }

    @Override
    public void save(HomeModel homeModel) {
        HomeModel home = homePersistencePort.getByName(homeModel.getName());
        if (home != null) {
            throw new HomeAlreadyExistsException();
        }
        homePersistencePort.save(homeModel);

    }

    @Override
    public List<HomeModel> get(Integer page, Integer size, boolean orderAsc) {
        return homePersistencePort.get(page, size, orderAsc);
    }
}
