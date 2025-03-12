package com.casas.casas.domain.ports.out;

import com.casas.casas.domain.model.HomeModel;

import java.util.List;

public interface HomePersistencePort {
    void save(HomeModel homeModel);
    HomeModel getByName(String homeName);
    List<HomeModel> get(Integer page, Integer size, boolean orderAsc);
}
