package K23CNT1.natProject3.dto;

import lombok.Data;

@Data
public class UserRegistrationDTO {

    private String username;    // Tên đăng nhập (hoặc Email)
    private String fullname;    // Họ và tên
    private String password;    // Mật khẩu
    private String confirmPassword; // Nhập lại mật khẩu

    // Bạn có thể thêm các logic validate ở Service
    // Ví dụ: check password.equals(confirmPassword)
}