package lk.ijse.shopManagement.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class CustomerDTO {
    private String id;
    private String name;
    private String address;
    private String contact;
    private String nic;
}
