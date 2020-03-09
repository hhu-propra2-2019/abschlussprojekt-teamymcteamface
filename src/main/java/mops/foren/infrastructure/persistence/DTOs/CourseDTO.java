package mops.foren.infrastructure.persistence.DTOs;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "course")
public class CourseDTO {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String semester;

    private String lecturer;


    @OneToOne(mappedBy = "course")
    private ForumDTO forum;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_course",
            joinColumns = {@JoinColumn(name = "id")},
            inverseJoinColumns = {@JoinColumn(name = "username")})
    private Set<UserDTO> users;
}
