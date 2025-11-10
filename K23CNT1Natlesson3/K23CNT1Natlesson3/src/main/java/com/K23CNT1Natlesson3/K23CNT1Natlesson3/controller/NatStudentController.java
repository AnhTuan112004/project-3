package com.K23CNT1Natlesson3.K23CNT1Natlesson3.controller;

import com.K23CNT1Natlesson3.K23CNT1Natlesson3.entity.NatStudent;
import com.K23CNT1Natlesson3.K23CNT1Natlesson3.service.NatStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/natstudent") // Đường dẫn gốc cho controller
public class NatStudentController {

    @Autowired
    private NatStudentService natStudentService;

    /**
     * API lấy danh sách sinh viên
     * (GET localhost:8080/natstudent/list)
     */
    @GetMapping("/list")
    public List<NatStudent> getAllStudents() {
        return natStudentService.getAllStudents();
    }

    /**
     * API lấy sinh viên theo ID
     * (GET localhost:8080/natstudent/1)
     */
    @GetMapping("/{id}")
    public NatStudent getStudentById(@PathVariable String id) {
        Long param = Long.parseLong(id);
        return natStudentService.getStudent(param);
    }

    /**
     * API thêm mới sinh viên
     * (POST localhost:8080/natstudent/add)
     */
    @PostMapping("/add")
    public NatStudent addStudent(@RequestBody NatStudent student) {
        return natStudentService.addStudent(student);
    }

    /**
     * API cập nhật sinh viên
     * (PUT localhost:8080/natstudent/1)
     */
    @PutMapping("/{id}")
    public NatStudent updateStudent(@PathVariable String id, @RequestBody NatStudent student) {
        Long param = Long.parseLong(id);
        return natStudentService.updateStudent(param, student);
    }

    /**
     * API xóa sinh viên
     * (DELETE localhost:8080/natstudent/1)
     */
    @DeleteMapping("/{id}")
    public boolean deleteStudent(@PathVariable String id) {
        Long param = Long.parseLong(id);
        return natStudentService.deleteStudent(param);
    }
}