package K23CNT1.natProject3.dto;

import K23CNT1.natProject3.entity.User;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;

public class CustomUserDetailsDOT extends org.springframework.security.core.userdetails.User {

    private final String fullName;
    private final String rankLevel; // Hoặc userRank tùy theo tên field trong Entity User

    public CustomUserDetailsDOT(User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getUsername(), user.getPassword(), authorities);
        this.fullName = user.getFullName();
        this.rankLevel = user.getRankLevel(); // Lấy từ nat_rank_level trong DB
    }

    public String getFullName() { return fullName; }
    public String getRankLevel() { return rankLevel; }
}