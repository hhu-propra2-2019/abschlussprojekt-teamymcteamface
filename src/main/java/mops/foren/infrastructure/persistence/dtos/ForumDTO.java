package mops.foren.infrastructure.persistence.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "forum")
public class ForumDTO {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String description;


    @OneToMany(mappedBy = "forum", fetch = FetchType.LAZY)
    private Set<TopicDTO> topics;
}
