package lk.ijse.shopManagement.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class SupplierDTO {
    private String id;
    private String supplierName;
    private String supplierCompany;
    private String contact;
}
