package lk.ijse.shopManagement.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Details {
    private String so_id;
    private String code;
    private double amount;
    private int qty;
}
