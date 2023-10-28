package lk.ijse.shopManagement.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Delivery {
    private String id;
    private int distance;
    private double amount;
}
