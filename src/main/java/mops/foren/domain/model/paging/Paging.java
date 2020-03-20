package mops.foren.domain.model.paging;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Paging {
    boolean isFirst;
    boolean isLast;
    boolean hasContent;
    int totalPages;
    long totalElements;
    int currentPage;
}
