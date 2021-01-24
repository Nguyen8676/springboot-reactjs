package com.example.demo.controller;

import com.example.demo.entity.Employee;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Executable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    //get all employees
    @GetMapping("/employees")
    public List<Employee> getAllEmployee(){
        return employeeRepository.findAll();
    }

    @PostMapping("/createEmployee")
    public Employee createEmployee(@RequestBody Employee employee){
                return employeeRepository.save(employee);
    }

    @GetMapping("/getEmployeeById/{id}")
    @ResponseBody
    public Employee getEmployeeById(@PathVariable Long id){
        return employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("not found employee"));

    }

    @PutMapping("/updateEmployee/{id}")
    @ResponseBody
    public Employee updateEmployee(@PathVariable Long id,@RequestBody Employee employeeDetails){

        Employee employee=employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("not found employee"));

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmailId(employeeDetails.getEmailId());

        Employee updateEmployee=employeeRepository.save(employee);
        return updateEmployee;
    }

    @DeleteMapping("/deleteEmployee/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteEmployee(@PathVariable Long id){
        Employee employee=employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("not found employee"));
        employeeRepository.delete(employee);
        Map<String,Boolean> respone=new HashMap<>();
        respone.put("deleted",Boolean.TRUE);
        return ResponseEntity.ok(respone);
    }
}
