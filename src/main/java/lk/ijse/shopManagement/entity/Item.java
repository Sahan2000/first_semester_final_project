package lk.ijse.shopManagement.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Item {
    String itemCode;
    String itemName;
    double sellPrice;
    double getPrice;
    String itemType;
    String supplier;
    int quantity;
}
