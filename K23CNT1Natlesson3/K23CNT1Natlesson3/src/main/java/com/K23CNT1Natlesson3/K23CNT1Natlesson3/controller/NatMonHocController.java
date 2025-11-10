package com.K23CNT1Natlesson3.K23CNT1Natlesson3.controller;

import com.K23CNT1Natlesson3.K23CNT1Natlesson3.entity.NatMonHoc;
import com.K23CNT1Natlesson3.K23CNT1Natlesson3.service.NatMonHocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/natmonhoc") // Đổi đường dẫn
public class NatMonHocController {

    @Autowired
    private NatMonHocService natMonHocService;

    @GetMapping("/list")
    public List<NatMonHoc> getAllNatMonHoc() {
        return natMonHocService.getAllNatMonHoc();
    }

    @GetMapping("/{mamh}")
    public NatMonHoc getNatMonHocByMaMh(@PathVariable String mamh) {
        return natMonHocService.getNatMonHocByMaMh(mamh);
    }

    @PostMapping("/add")
    public NatMonHoc addNatMonHoc(@RequestBody NatMonHoc natMonHoc) {
        return natMonHocService.addNatMonHoc(natMonHoc);
    }

    @PutMapping("/{mamh}")
    public NatMonHoc updateNatMonHoc(@PathVariable String mamh, @RequestBody NatMonHoc natMonHoc) {
        return natMonHocService.updateNatMonHoc(mamh, natMonHoc);
    }

    @DeleteMapping("/{mamh}")
    public boolean deleteNatMonHoc(@PathVariable String mamh) {
        return natMonHocService.deleteNatMonHoc(mamh);
    }
}