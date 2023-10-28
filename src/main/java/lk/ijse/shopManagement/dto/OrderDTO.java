package lk.ijse.shopManagement.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class OrderDTO {
    private String id;
    private LocalDate date;
    private LocalTime time;
    private String custId;
    private String status;
}
