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
@Table(name = "thread")
public class ThreadDTO {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @ManyToOne
    private TopicDTO topic;

    @OneToMany(mappedBy = "thread", fetch = FetchType.LAZY)
    private List<PostDTO> posts;

}
