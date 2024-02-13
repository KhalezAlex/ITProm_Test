package org.klozevitz.itprom_test.model;

import jakarta.persistence.*;
import lombok.*;
import org.klozevitz.itprom_test.model.dto.DepartmentDTO;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "departments_t")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", nullable = false, length = 30)
    private String name;
    @Column(name = "note")
    private String note;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private Department parentDpt;
    @OneToMany(mappedBy = "parentDpt", cascade = CascadeType.ALL)
    private Set<Department> subDepartments = new HashSet<>();


    public Department(String name, String note) {
        this.name = name;
        this.note = note;
        this.parentDpt = null;
    }

    public Department(String name, String note, Department department) {
        this.name = name;
        this.note = note;
        this.parentDpt = department;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", note='" + note + '\'' +
                ", parentDpt=" + (parentDpt == null ? "" : parentDpt.id) +
                '}';
    }

    public static Department fromDto(DepartmentDTO dto, Department parent) {
        return Department.builder()
                .name(dto.getName())
                .note(dto.getNote())
                .parentDpt(parent)
                .build();
    }
}
