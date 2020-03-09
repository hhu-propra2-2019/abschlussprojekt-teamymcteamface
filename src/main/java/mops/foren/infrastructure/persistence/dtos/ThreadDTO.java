package mops.foren.infrastructure.persistence.dtos;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "thread")
public class ThreadDTO {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @ManyToOne
    private TopicDTO topic;

    @OneToMany(mappedBy = "thread")
    private Set<PostDTO> posts;

}
