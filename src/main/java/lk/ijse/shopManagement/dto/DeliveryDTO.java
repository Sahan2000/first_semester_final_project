package lk.ijse.shopManagement.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class DeliveryDTO {
    private String id;
    private int distance;
    private double amount;
}
