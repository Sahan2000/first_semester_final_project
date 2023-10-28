package lk.ijse.shopManagement.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class HavePayInvoiceDTO {
    private String id;
    private String date;
    private String supid;
    private double amount;
}
