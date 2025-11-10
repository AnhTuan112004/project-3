package com.K23CNT1Natlesson3.K23CNT1Natlesson3.service;

import com.K23CNT1Natlesson3.K23CNT1Natlesson3.entity.NatMonHoc;
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
public class NatMonHocService {

    private List<NatMonHoc> listNatMonHoc;

    public NatMonHocService() {
        listNatMonHoc = new ArrayList<>();
        listNatMonHoc.addAll(Arrays.asList(
                new NatMonHoc("OOP", "Lập trình hướng đối tượng", 45),
                new NatMonHoc("DB", "Cơ sở dữ liệu", 45),
                new NatMonHoc("WEB", "Phát triển ứng dụng Web", 60),
                new NatMonHoc("PRJ", "Dự án mẫu", 30),
                new NatMonHoc("ENG", "Tiếng Anh chuyên ngành", 30)
        ));
    }

    public List<NatMonHoc> getAllNatMonHoc() {
        return listNatMonHoc;
    }

    public NatMonHoc getNatMonHocByMaMh(String mamh) {
        return listNatMonHoc.stream()
                .filter(m -> m.getNatMamh().equalsIgnoreCase(mamh))
                .findFirst().orElse(null);
    }

    public NatMonHoc addNatMonHoc(NatMonHoc natMonHoc) {
        listNatMonHoc.add(natMonHoc);
        return natMonHoc;
    }

    public NatMonHoc updateNatMonHoc(String mamh, NatMonHoc natMonHoc) {
        NatMonHoc find = getNatMonHocByMaMh(mamh);
        if (find != null) {
            find.setNatTenmh(natMonHoc.getNatTenmh());
            find.setNatSotiet(natMonHoc.getNatSotiet());
            return find;
        }
        return null;
    }

    public boolean deleteNatMonHoc(String mamh) {
        NatMonHoc find = getNatMonHocByMaMh(mamh);
        if (find != null) {
            return listNatMonHoc.remove(find);
        }
        return false;
    }
}