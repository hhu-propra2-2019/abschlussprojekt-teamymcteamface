package mops.foren.infrastructure.persistence.dtos;

import lombok.*;

import javax.persistence.*;
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
}
