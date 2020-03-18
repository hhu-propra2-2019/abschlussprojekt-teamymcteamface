package mops.foren.infrastructure.persistence.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "post")
public class PostDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String author;


    @CreationTimestamp
    private LocalDateTime dateTime;

    @ManyToOne
    private ThreadDTO thread;

    @ManyToOne
    private UserDTO user;

    private String text;
}
