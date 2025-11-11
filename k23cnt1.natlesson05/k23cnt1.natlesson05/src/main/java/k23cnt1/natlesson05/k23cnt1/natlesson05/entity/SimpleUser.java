package k23cnt1.natlesson05.k23cnt1.natlesson05.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleUser {
    private int id;
    private String username;
    private String email;
    private boolean status;
}