package com.casas.casas.domain.ports.in;

import com.casas.casas.domain.model.HomeModel;

import java.util.List;

public interface HomeServicePort {
    void save(HomeModel homeModel);
    List<HomeModel> get(Integer page, Integer size, boolean orderAsc);
}
