package lk.ijse.shopManagement.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class loadTM {
    private String itemid;
    private int qty;
    private double price;
}

