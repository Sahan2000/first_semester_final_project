package lk.ijse.shopManagement.dto.tm;

import lombok.*;
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EmployeeTM {
    private String id;
    private String name;
    private String address;
    private String contact;
    private String jobRoll;
    private double salary;
}
