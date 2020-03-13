package mops.foren.infrastructure.persistence.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

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
    @GeneratedValue
    private Long id;
    private String author;


    @CreatedDate
    private LocalDateTime dateTime;


    @ManyToOne
    private ThreadDTO thread;

    @ManyToOne
    private UserDTO user;
}
