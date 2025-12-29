package K23CNT1.natProject3.dto;

import lombok.Data;

@Data
public class UserRegisterDTO {
    private String username;
    private String password;
    private String confirmPassword; // Dùng để check validation xem khớp pass không
    private String fullName;
    private String email;
    private String phone;
}