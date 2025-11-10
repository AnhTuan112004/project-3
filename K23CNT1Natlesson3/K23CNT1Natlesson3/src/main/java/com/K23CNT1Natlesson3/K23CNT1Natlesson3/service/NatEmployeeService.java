package com.K23CNT1Natlesson3.K23CNT1Natlesson3.service;

import com.K23CNT1Natlesson3.K23CNT1Natlesson3.entity.NatEmployee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * @author Anh Tuấn
 * @version 1.0
 * Date 10/11/2025
 */
@Service
public class NatEmployeeService {

    private List<NatEmployee> listNatEmployee;

    public NatEmployeeService() {
        listNatEmployee = new ArrayList<>();
        listNatEmployee.addAll(Arrays.asList(
                new NatEmployee(1L, "Nguyễn Văn A", "Nam", 30, 15000000),
                new NatEmployee(2L, "Trần Thị B", "Nữ", 25, 12000000),
                new NatEmployee(3L, "Lê Văn C", "Nam", 42, 25000000),
                new NatEmployee(4L, "Phạm Thị D", "Nữ", 28, 18000000),
                new NatEmployee(5L, "Hoàng Văn E", "Nam", 35, 20000000)
        ));
    }

    public List<NatEmployee> getAllNatEmployees() {
        return listNatEmployee;
    }

    public NatEmployee getNatEmployeeById(Long id) {
        return listNatEmployee.stream()
                .filter(e -> e.getNatId().equals(id))
                .findFirst().orElse(null);
    }

    public NatEmployee addNatEmployee(NatEmployee natEmployee) {
        listNatEmployee.add(natEmployee);
        return natEmployee;
    }

    public NatEmployee updateNatEmployee(Long id, NatEmployee natEmployee) {
        NatEmployee find = getNatEmployeeById(id);
        if (find != null) {
            find.setNatFullName(natEmployee.getNatFullName());
            find.setNatGender(natEmployee.getNatGender());
            find.setNatAge(natEmployee.getNatAge());
            find.setNatSalary(natEmployee.getNatSalary());
            return find;
        }
        return null;
    }

    public boolean deleteNatEmployee(Long id) {
        NatEmployee find = getNatEmployeeById(id);
        if (find != null) {
            return listNatEmployee.remove(find);
        }
        return false;
    }
}