package com.maha.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.maha.springboot.exception.ResourceNotFoundException;
import com.maha.springboot.model.Employee;
import com.maha.springboot.repository.EmployeeRepository;

@CrossOrigin
@RestController
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();
	}
	
	//create employee api
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee emp) {
		return employeeRepository.save(emp);
	}
	
	//getemployee by id
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long id) {
		
		Employee emp = employeeRepository.findById(id)
						.orElseThrow(()->
							new ResourceNotFoundException("Employee not found with this id : "+id));
		return ResponseEntity.ok(emp);
	}
	
	//update employee
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Long id,@RequestBody Employee emp){
		
		Employee e = employeeRepository.findById(id)
				.orElseThrow(()->
					new ResourceNotFoundException("Employee not found with this id : "+id));
		e.setEmailId(emp.getEmailId());
		e.setFirstName(emp.getFirstName());
		e.setLastName(emp.getLastName());
		
		return ResponseEntity.ok(employeeRepository.save(e));
	}
	
	//delete employee
	@DeleteMapping("/employees/{id}")
	public Map<String,Boolean> deleteEmployee(@PathVariable("id") Long id){
		Employee e = employeeRepository.findById(id)
				.orElseThrow(()->
					new ResourceNotFoundException("Employee not found with this id : "+id));
		employeeRepository.deleteById(id);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
