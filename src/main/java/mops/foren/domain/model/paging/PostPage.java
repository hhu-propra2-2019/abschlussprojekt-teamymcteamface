package mops.foren.domain.model.paging;

import lombok.AllArgsConstructor;
import lombok.Data;
import mops.foren.domain.model.Post;

import java.util.List;

@AllArgsConstructor
@Data
public class PostPage {
    Paging paging;
    List<Post> posts;
}
