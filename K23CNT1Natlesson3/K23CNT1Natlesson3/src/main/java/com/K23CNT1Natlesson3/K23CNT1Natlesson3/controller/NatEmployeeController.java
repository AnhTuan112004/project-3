package com.K23CNT1Natlesson3.K23CNT1Natlesson3.controller;

import com.K23CNT1Natlesson3.K23CNT1Natlesson3.entity.NatEmployee;
import com.K23CNT1Natlesson3.K23CNT1Natlesson3.service.NatEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/natemployee") // Đổi đường dẫn
public class NatEmployeeController {

    @Autowired
    private NatEmployeeService natEmployeeService;

    @GetMapping("/list")
    public List<NatEmployee> getAllNatEmployees() {
        return natEmployeeService.getAllNatEmployees();
    }

    @GetMapping("/{id}")
    public NatEmployee getNatEmployeeById(@PathVariable Long id) {
        return natEmployeeService.getNatEmployeeById(id);
    }

    @PostMapping("/add")
    public NatEmployee addNatEmployee(@RequestBody NatEmployee natEmployee) {
        return natEmployeeService.addNatEmployee(natEmployee);
    }

    @PutMapping("/{id}")
    public NatEmployee updateNatEmployee(@PathVariable Long id, @RequestBody NatEmployee natEmployee) {
        return natEmployeeService.updateNatEmployee(id, natEmployee);
    }

    @DeleteMapping("/{id}")
    public boolean deleteNatEmployee(@PathVariable Long id) {
        return natEmployeeService.deleteNatEmployee(id);
    }
}