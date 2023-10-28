package lk.ijse.shopManagement.dto;

import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EmployeeDTO {
    private String id;
    private String name;
    private String address;
    private String contact;
    private String jobRoll;
    private double salary;
}
