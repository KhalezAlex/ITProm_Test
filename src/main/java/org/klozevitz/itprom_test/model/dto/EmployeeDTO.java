package org.klozevitz.itprom_test.model.dto;

import lombok.Builder;
import lombok.Data;
import org.klozevitz.itprom_test.model.Employee;

@Data
@Builder
public class EmployeeDTO {
    private int id;
    private String fio;
    private String note;
    private int profId;
    private int deptId;

    public static EmployeeDTO fromEntity(Employee employee) {
        return EmployeeDTO.builder()
                .id(employee.getId())
                .fio(employee.getFio())
                .note(employee.getNote())
                .profId(employee.getProfession().getId())
                .deptId(employee.getDepartment().getId())
                .build();
    }
}
