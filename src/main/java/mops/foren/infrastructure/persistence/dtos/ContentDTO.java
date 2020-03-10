package mops.foren.infrastructure.persistence.dtos;

import lombok.Data;
import mops.foren.domain.model.ContentType;

import javax.persistence.*;

@Entity
@Data
@Table(name = "content")
public class ContentDTO {
    @Id
    @GeneratedValue
    private Long id;

    private Long sizeKB;

    private String link;

    @Lob
    private String message;

    private ContentType contentType;

    @ManyToOne
    private PostDTO post;

}
