package mops.foren.infrastructure.persistence.mapper;

import mops.foren.domain.model.Thread;
import mops.foren.domain.model.paging.Paging;
import mops.foren.domain.model.paging.PostPage;
import mops.foren.domain.model.paging.ThreadPage;
import mops.foren.infrastructure.persistence.dtos.ThreadDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public abstract class ThreadPageMapper {

    /**
     * Map the Spring page to our page-type.
     *
     * @param dtoPage page of spring
     * @param page    the number of the page
     * @return Our PostPage object
     */
    public static ThreadPage toThreadPage(Page<ThreadDTO> dtoPage, int page) {
        List<Thread> collect = dtoPage.stream()
                .map(ThreadMapper::mapThreadDtoToThread)
                .collect(Collectors.toList());

        Paging paging = new Paging(dtoPage.isFirst(), dtoPage.isLast(),
                dtoPage.hasContent(), dtoPage.getTotalPages(), dtoPage.getTotalElements(), page);

        return new ThreadPage(paging, collect);
    }

}
