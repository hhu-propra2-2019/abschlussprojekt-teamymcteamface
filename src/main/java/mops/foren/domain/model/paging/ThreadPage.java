package mops.foren.domain.model.paging;

import lombok.AllArgsConstructor;
import lombok.Data;
import mops.foren.domain.model.Post;
import mops.foren.domain.model.Thread;

import java.util.List;

@AllArgsConstructor
@Data
public class ThreadPage {
    Paging paging;
    List<Thread> threads;
}
