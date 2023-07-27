package com.shopme.admin.setting.country;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.repository.core.support.RepositoryComposition;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Country;
import com.shopme.common.entity.State;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CountryRepositoryTests {
	
	@Autowired CountryRepository repo;
	
	
	@Test
	public void testCreateCountry() {
		Country usa = new Country("China", "103");
		Set<State> states = new HashSet<>();
		states.add(new State("Shanghai",usa));

//		states.add(new State("AZ",usa));
//		states.add(new State("OR",usa));
//		usa.setStates(states);
		
		Country savedCountry = repo.save(usa);
		assertThat(savedCountry.getId()).isGreaterThan(0);
	}
	
	
	@Test
	public void listCountries() {
		List<Country> listCountries = repo.findAllByOrderByNameAsc();
		listCountries.forEach(System.out::println);
		
		assertThat(listCountries.size()).isGreaterThan(0);
	}
	
	@Test
	public void updateCountry() {
		Country country = repo.findById(1).get();
		country.setName("United States of America");
		Country savedCountry = repo.save(country);
		
		assertThat(savedCountry.getName() == "United States of America");
	}
	
	@Test
	public void deleteCountry() {
		Country country = repo.findById(4).get();
		repo.delete(country);
		
		assertThat(repo.findById(4).isEmpty());
	}

}
