package mops.foren.domain.model.paging;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Paging {
    Boolean isFirst;
    Boolean isLast;
    Boolean hasContent;
    Integer totalPages;
    Long totalElements;
    Integer currentPage;
}
