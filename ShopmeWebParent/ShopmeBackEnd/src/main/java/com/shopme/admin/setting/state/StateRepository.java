package com.shopme.admin.setting.state;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.shopme.common.entity.Country;
import com.shopme.common.entity.State;


public interface StateRepository extends PagingAndSortingRepository<State, Integer> {
	
	public List<State> findByCountryOrderByNameAsc(Country country);

}
