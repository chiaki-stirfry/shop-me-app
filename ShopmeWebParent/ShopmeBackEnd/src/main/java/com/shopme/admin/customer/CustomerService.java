package com.shopme.admin.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopme.admin.paging.PagingAndSortingHelper;
import com.shopme.admin.setting.country.CountryRepository;
import com.shopme.common.entity.Country;
import com.shopme.common.entity.Customer;


@Service
@Transactional
public class CustomerService {

		@Autowired private CountryRepository countryRepo;
		@Autowired private CustomerRepository customerRepo;
		@Autowired PasswordEncoder passwordEncoder;
		
		public static final Integer CUSTOMERS_PER_PAGE = 10;
		
		public void listByPage(int pageNum, PagingAndSortingHelper helper) {
			helper.listEntities(pageNum, CUSTOMERS_PER_PAGE, customerRepo);
		}
		
		public void updateCustomerEnabledStatus(int id, boolean enabled) {
			customerRepo.updateEnabledStatus(id, enabled);
		}
		
		public Customer get(int id){
			return customerRepo.findById(id).get();
		}
		
		public List<Country> listAllCountries(){
			return countryRepo.findAllByOrderByNameAsc();
		}
		
		public boolean isEmailUnique(Integer id, String email) {
			Customer existCustomer = customerRepo.findByEmail(email);

			if (existCustomer != null && existCustomer.getId() != id) {
				// found another customer having the same email
				return false;
			}
			
			return true;
		}
		
		public void save(Customer customerInForm) {
			Customer customerInDB = customerRepo.findById(customerInForm.getId()).get();
			if (!customerInForm.getPassword().isEmpty()) {
				String encodedPassword = passwordEncoder.encode(customerInForm.getPassword());
				customerInForm.setPassword(encodedPassword);			
			} else {
				
				customerInForm.setPassword(customerInDB.getPassword());
			}		
			
			customerInForm.setEnabled(customerInDB.isEnabled());
			customerInForm.setCreatedTime(customerInDB.getCreatedTime());
			customerInForm.setVerificationCode(customerInDB.getVerificationCode());
			
			customerRepo.save(customerInForm);
		}

		

		public void delete(Integer id) throws CustomerNotFoundException {
			Long count = customerRepo.countById(id);
			if (count == null || count == 0) {
				throw new CustomerNotFoundException("Could not find any customers with ID " + id);
			}
			
			customerRepo.deleteById(id);
		}
	
}
