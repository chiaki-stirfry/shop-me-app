package com.shopme.admin.customer;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.paging.PagingAndSortingHelper;
import com.shopme.admin.paging.PagingAndSortingParam;
import com.shopme.common.entity.Country;
import com.shopme.common.entity.Customer;




@Controller
public class CustomerController {
	private String defaultRedirectURL = "redirect:/customers/page/1?sortField=firstName&sortDir=asc";
	
	@Autowired private CustomerService customerService;
	
	
	@GetMapping("/customers")
	public String listFirstPage(Model model) {
		return defaultRedirectURL;
	}
	
	@GetMapping("/customers/page/{pageNum}")
	public String listByPage(
			@PagingAndSortingParam(listName = "listCustomers", moduleURL = "/customers") PagingAndSortingHelper helper,
			@PathVariable(name = "pageNum") int pageNum) {

		customerService.listByPage(pageNum, helper);
		
		return "customers/customers";
	}
	
	
	@GetMapping("/customers/{id}/enabled/{status}")
	public String updateCustomerEnabledStatus(@PathVariable("id") Integer id,
			@PathVariable("status") boolean enabled, RedirectAttributes redirectAttributes) {
		customerService.updateCustomerEnabledStatus(id, enabled);
		String status = enabled ? "enabled" : "disabled";
		String message = "The customer ID " + id + " has been " + status;
		redirectAttributes.addFlashAttribute("message", message);
		
		return defaultRedirectURL;
	}
	
	@GetMapping("/customers/detail/{id}")
	public String viewCustomer(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) throws CustomerNotFoundException {
		Customer customer = customerService.get(id);
		model.addAttribute("customer", customer);
		
		return "customers/customer_detail_modal";
	}
	
	
	
	@GetMapping("/customers/edit/{id}")
	public String editUser(@PathVariable(name = "id") Integer id, 
			Model model,
			RedirectAttributes redirectAttributes) throws CustomerNotFoundException {
		Customer customer = customerService.get(id);
		List<Country> listCountries = customerService.listAllCountries();
		
		model.addAttribute("listCountries", listCountries);
		model.addAttribute("customer", customer);
		model.addAttribute("pageTitle", "Edit Customer (ID: " + id + ")");
		
		
		return defaultRedirectURL;
	}
	
	
	
	
	
	@PostMapping("/customers/save")
	public String saveCustomer(Customer customer, RedirectAttributes redirectAttributes) throws IOException {
		
		customerService.save(customer);
		
		
		redirectAttributes.addFlashAttribute("message", "The customer has been saved successfully.");
		
		return defaultRedirectURL;
	}

	
	
	
	@GetMapping("/customers/delete/{id}")
	public String deleteCustomer(@PathVariable(name = "id") Integer id, 
			Model model,
			RedirectAttributes redirectAttributes) throws CustomerNotFoundException {
		customerService.delete(id);
		redirectAttributes.addFlashAttribute("message", 
				"The customer ID " + id + " has been deleted successfully");
		
		return defaultRedirectURL;
	}
	

	
	

	


}
