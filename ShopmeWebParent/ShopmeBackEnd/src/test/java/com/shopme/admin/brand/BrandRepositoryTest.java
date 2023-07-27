package com.shopme.admin.brand;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Category;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class BrandRepositoryTest {

	@Autowired
	private BrandRepository repo;
	

	
	@Test
	public void createBrand() {
		Brand brand = new Brand("Samsung");
		Set<Category> categories = new HashSet<>();
		categories.add(new Category(24));
		categories.add(new Category(29));
		brand.setCategories(categories);
		
		Brand savedBrand = repo.save(brand);
		assertThat(savedBrand.getId()).isGreaterThan(0);
		
	}
	
	@Test
	public void testListAllBrands() {
		Iterable<Brand> brands = repo.findAll();
		brands.forEach(brand -> System.out.println(brand));
		assertThat(brands).isNotEmpty();
	}
	
	@Test
	public void getByBrandId() {
		Brand brand = repo.findById(1).get();
		System.out.println(brand);
		
		assertThat(brand).isNotNull();
	}
	
	@Test
	public void testUptdateBrand() {
		Brand brand = repo.findById(3).get();
		brand.setName("Samsung Electronics");
		
		repo.save(brand);
	}
	@Test
	public void testDelete() {
		Integer id =4;
		repo.deleteById(id);
		
		Optional<Brand> result = repo.findById(id);
		
		assertThat(result.isEmpty());
	}
	
	
	
	
}
