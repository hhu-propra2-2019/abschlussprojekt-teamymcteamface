package mops.foren.infrastructure.persistence.dtos;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "forum")
public class ForumDTO {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String description;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_forum",
            joinColumns = {@JoinColumn(name = "id")},
            inverseJoinColumns = {@JoinColumn(name = "username")})
    private Set<UserDTO> users;

    @OneToMany(mappedBy = "forum", fetch = FetchType.LAZY)
    private Set<TopicDTO> topics;

    @OneToOne
    private CourseDTO course;
}
