package com.K23CNT1Natlesson3.K23CNT1Natlesson3.service;

import com.K23CNT1Natlesson3.K23CNT1Natlesson3.entity.NatStudent;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Service class: NatStudentService
 * <p>Lớp dịch vụ thực hiện các chức năng thao tác với List
 Object Student</p>
 *
 * @author Anh Tuấn
 * @version 1.0
 * Date 10/11/2025
 */
@Service
public class NatStudentService {

    private List<NatStudent> students;

    // Khởi tạo danh sách sinh viên mẫu
    public NatStudentService() {
        students = new ArrayList<>();
        students.addAll(Arrays.asList(
                new NatStudent(1L, "Đỗ Quyên", 25, "Nữ", "Hà Nội", "0987654321", "quyen@gmail.com"),
                new NatStudent(2L, "Văn Tuấn", 28, "Nam", "Hà Nam", "0123456789", "tuan@gmail.com"),
                new NatStudent(3L, "Đức Anh", 22, "Nam", "Thái Bình", "0987651234", "anh@gmail.com"),
                new NatStudent(4L, "Vũ Thơm", 21, "Nữ", "Hà Nội", "0987654321", "thom@gmail.com")
        ));
    }

    /**
     * Lấy danh sách sinh viên
     */
    public List<NatStudent> getAllStudents() {
        return students;
    }

    /**
     * Lấy sinh viên theo id
     */
    public NatStudent getStudent(Long id) {
        return students.stream()
                .filter(student -> student.getNatid().equals(id))
                .findFirst().orElse(null);
    }

    /**
     * Thêm mới sinh viên
     */
    public NatStudent addStudent(NatStudent student) {
        students.add(student);
        return student;
    }

    /**
     * Cập nhật thông tin sinh viên
     */
    public NatStudent updateStudent(Long id, NatStudent student) {
        NatStudent check = getStudent(id);
        if (check != null) {
            students.forEach(item -> {
                if (item.getNatid().equals(id)) {
                    item.setNatname(student.getNatname());
                    item.setNataddress(student.getNataddress());
                    item.setNatemail(student.getNatemail());
                    item.setNatphone(student.getNatphone());
                    item.setNatage(student.getNatage());
                    item.setNatgender(student.getNatgender());
                }
            });
            return student;
        }
        return null;
    }

    /**
     * Xóa thông tin sinh viên
     */
    public boolean deleteStudent(Long id) {
        NatStudent check = getStudent(id);
        if (check != null) {
            return students.remove(check);
        }
        return false;
    }
}