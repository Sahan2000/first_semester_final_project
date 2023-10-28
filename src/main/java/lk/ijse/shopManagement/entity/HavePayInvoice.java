package lk.ijse.shopManagement.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class HavePayInvoice {
    private String id;
    private String date;
    private String supid;
    private double amount;
}
