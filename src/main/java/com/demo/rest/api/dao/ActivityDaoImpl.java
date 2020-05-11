package com.demo.rest.api.dao;

import com.demo.rest.api.entity.Activity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ActivityDaoImpl implements DaoRepository<Activity, Long> {

    @Override
    public List<Activity> findAll() {
        return null;
    }

    @Override
    public Optional<Activity> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public <S extends Activity> S save(S entity) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }
}
