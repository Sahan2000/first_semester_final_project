package lk.ijse.shopManagement.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class SupplierOrder {
    private String sup_order_id;
    private String date;
    private String supplierid;
    private double amount;
    private String status;
}
