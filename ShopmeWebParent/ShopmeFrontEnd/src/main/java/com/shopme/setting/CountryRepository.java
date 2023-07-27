package com.shopme.setting;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.shopme.common.entity.Country;


public interface CountryRepository extends PagingAndSortingRepository<Country, Integer> {
	public List<Country> findAllByOrderByNameAsc();

}
