package mops.foren.infrastructure.persistence.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "topic")
public class TopicDTO {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String description;

    private Boolean moderated;


    @ManyToOne
    private ForumDTO forum;

    @OneToMany(mappedBy = "topic", fetch = FetchType.LAZY)
    private List<ThreadDTO> threads;

}
