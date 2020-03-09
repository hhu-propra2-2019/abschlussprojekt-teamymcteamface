package mops.foren.infrastructure.persistence.DTOs;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
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

    @OneToMany(mappedBy = "topic")
    private Set<ThreadDTO> threads;

}
