package mops.foren.infrastructure.persistence.DTOs;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "user")
public class UserDTO {
    @Id
    private String username;

    private String name;

    private String email;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_course",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private Set<CourseDTO> courses;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_forum",
            joinColumns = {@JoinColumn(name = "username")},
            inverseJoinColumns = {@JoinColumn(name = "id")})
    private Set<ForumDTO> forums;
}
