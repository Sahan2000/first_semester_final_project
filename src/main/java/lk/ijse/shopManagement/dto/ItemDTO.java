package lk.ijse.shopManagement.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class ItemDTO {
    String itemCode;
    String itemName;
    double sellPrice;
    double getPrice;
    String itemType;
    String supplier;
    int quantity;
}
