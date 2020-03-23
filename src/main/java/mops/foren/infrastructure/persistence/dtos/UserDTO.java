package mops.foren.infrastructure.persistence.dtos;

import lombok.*;
import mops.foren.domain.model.Role;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = "forums")
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@Builder
@Entity
@Table(name = "user")
public class UserDTO {
    @Id
    private String username;

    private String name;

    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_forum",
            joinColumns = {@JoinColumn(name = "username")},
            inverseJoinColumns = {@JoinColumn(name = "id")})
    private Set<ForumDTO> forums;

    /**
     * Creates role-table (automatically) and maps roles of user
     * inside forums in this Map.
     * Only saves special permissions > student.
     * Key: ForumId
     * Value: Role(ID)
     */
    @ElementCollection(targetClass = Role.class)
    @MapKeyColumn(name = "forumId")
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "username"))
    private Map<Long, Role> roles;

    @OneToMany(mappedBy = "author")
    private List<PostDTO> posts;

    @OneToMany(mappedBy = "author")
    private List<ThreadDTO> threads;
}
