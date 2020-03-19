package mops.foren.infrastructure.persistence.dtos;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Data
@EqualsAndHashCode(exclude = "forums")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class UserDTO {
    @Id
    private String username;

    private String name;

    private String email;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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
    @ElementCollection
    @MapKeyColumn(name = "forumId")
    @Column(name = "role")
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "username"))
    private Map<Long, Long> roles;

    @OneToMany(mappedBy = "author")
    private List<PostDTO> posts;
}
