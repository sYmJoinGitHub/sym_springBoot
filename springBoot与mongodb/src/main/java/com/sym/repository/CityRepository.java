package com.sym.repository;

import com.sym.repository.domain.CityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

/**
 * @author shenym
 * @date 2020/3/25 17:25
 */

public interface CityRepository extends Repository<CityEntity, Integer> {

    Page<CityEntity> findAll(Pageable pageable);

    CityEntity findByNameAndStateAllIgnoringCase(String name, String state);
}
