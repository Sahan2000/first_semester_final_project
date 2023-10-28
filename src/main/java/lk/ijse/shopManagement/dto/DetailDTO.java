package lk.ijse.shopManagement.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class DetailDTO {
    private String so_id;
    private String code;
    private double amount;
    private int qty;

}
