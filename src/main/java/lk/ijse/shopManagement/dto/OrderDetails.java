package lk.ijse.shopManagement.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class OrderDetails {
//
    private String Itemid;
    private String oid;
    private int qty;
    private double amount;
}
