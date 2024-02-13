package org.klozevitz.itprom_test.model.dto;

import lombok.Builder;
import lombok.Data;
import org.klozevitz.itprom_test.model.Department;

@Data
@Builder
public class DepartmentDTO {
    private int id;
    private String name;
    private String note;
    private int parentId;
    private String parentName;

    public static DepartmentDTO fromEntity(Department department) {
        return DepartmentDTO.builder()
                .id(department.getId())
                .name(department.getName())
                .note(department.getNote())
                .parentId(department.getParentDpt() == null ? 0 : department.getParentDpt().getId())
                .parentName(department.getParentDpt() == null ? "" : department.getParentDpt().getName())
                .build();
    }
}
