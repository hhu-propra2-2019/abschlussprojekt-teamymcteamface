package mops.foren.infrastructure.persistence.dtos;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "post")
public class PostDTO {
    @Id
    @GeneratedValue
    private Long id;
    private String author;


    @CreatedDate
    private LocalDateTime dateTime;


    @ManyToOne
    private ThreadDTO thread;

    @ManyToOne
    private UserDTO user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private List<ContentDTO> contents;
}
