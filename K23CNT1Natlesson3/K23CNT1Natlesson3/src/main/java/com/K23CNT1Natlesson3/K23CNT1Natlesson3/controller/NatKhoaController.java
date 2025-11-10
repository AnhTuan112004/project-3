package com.K23CNT1Natlesson3.K23CNT1Natlesson3.controller;

import com.K23CNT1Natlesson3.K23CNT1Natlesson3.entity.NatKhoa;
import com.K23CNT1Natlesson3.K23CNT1Natlesson3.service.NatKhoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/natkhoa") // Đổi đường dẫn
public class NatKhoaController {

    @Autowired
    private NatKhoaService natKhoaService;

    // GET (Lấy toàn bộ danh sách)
    @GetMapping("/list")
    public List<NatKhoa> getAllNatKhoa() {
        return natKhoaService.getAllNatKhoa();
    }

    // GET (Lấy theo makh)
    @GetMapping("/{makh}")
    public NatKhoa getNatKhoaByMaKh(@PathVariable String makh) {
        return natKhoaService.getNatKhoaByMaKh(makh);
    }

    // POST (Thêm mới)
    @PostMapping("/add")
    public NatKhoa addNatKhoa(@RequestBody NatKhoa natKhoa) {
        return natKhoaService.addNatKhoa(natKhoa);
    }

    // PUT (Cập nhật)
    @PutMapping("/{makh}")
    public NatKhoa updateNatKhoa(@PathVariable String makh, @RequestBody NatKhoa natKhoa) {
        return natKhoaService.updateNatKhoa(makh, natKhoa);
    }

    // DELETE (Xóa)
    @DeleteMapping("/{makh}")
    public boolean deleteNatKhoa(@PathVariable String makh) {
        return natKhoaService.deleteNatKhoa(makh);
    }
}