package mops.foren.infrastructure.persistence.mapper;

import mops.foren.domain.model.Post;
import mops.foren.domain.model.paging.Paging;
import mops.foren.domain.model.paging.PostPage;
import mops.foren.infrastructure.persistence.dtos.PostDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public abstract class PostPageMapper {

    /**
     * Map the Spring page to our page-type.
     *
     * @param dtoPage page of spring
     * @param page    the number of the page
     * @return Our PostPage object
     */
    public static PostPage toPostPage(Page<PostDTO> dtoPage, int page) {
        List<Post> collect = dtoPage.getContent().stream()
                .map(PostMapper::mapPostDtoToPost)
                .collect(Collectors.toList());

        Paging paging = new Paging(dtoPage.isFirst(), dtoPage.isLast(),
                dtoPage.hasContent(), dtoPage.getTotalPages(), dtoPage.getTotalElements(), page);

        return new PostPage(paging, collect);
    }

}
