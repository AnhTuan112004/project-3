package com.K23CNT1Natlesson3.K23CNT1Natlesson3.service;

import com.K23CNT1Natlesson3.K23CNT1Natlesson3.entity.NatKhoa;
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
public class NatKhoaService {

    private List<NatKhoa> listNatKhoa;

    // Constructor: Tạo danh sách 5 khoa
    public NatKhoaService() {
        listNatKhoa = new ArrayList<>();
        listNatKhoa.addAll(Arrays.asList(
                new NatKhoa("CNTT", "Công nghệ thông tin"),
                new NatKhoa("KT", "Kế toán"),
                new NatKhoa("QTKD", "Quản trị kinh doanh"),
                new NatKhoa("NN", "Ngôn ngữ Anh"),
                new NatKhoa("CK", "Cơ khí")
        ));
    }

    // Lấy toàn bộ danh sách
    public List<NatKhoa> getAllNatKhoa() {
        return listNatKhoa;
    }

    // Lấy danh sách theo makh
    public NatKhoa getNatKhoaByMaKh(String makh) {
        return listNatKhoa.stream()
                .filter(k -> k.getNatMakh().equalsIgnoreCase(makh))
                .findFirst().orElse(null);
    }

    // Thêm mới một khoa
    public NatKhoa addNatKhoa(NatKhoa natKhoa) {
        listNatKhoa.add(natKhoa);
        return natKhoa;
    }

    // Sửa đổi thông tin khoa
    public NatKhoa updateNatKhoa(String makh, NatKhoa natKhoa) {
        NatKhoa find = getNatKhoaByMaKh(makh);
        if (find != null) {
            find.setNatTenkh(natKhoa.getNatTenkh());
            return find;
        }
        return null;
    }

    // Xóa thông tin khoa
    public boolean deleteNatKhoa(String makh) {
        NatKhoa find = getNatKhoaByMaKh(makh);
        if (find != null) {
            return listNatKhoa.remove(find);
        }
        return false;
    }
}