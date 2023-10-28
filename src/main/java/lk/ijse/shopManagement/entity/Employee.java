package lk.ijse.shopManagement.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Employee {
    String id;
    String name;
    String address;
    String jobRoll;
    String contact;
    Double salary;
}
