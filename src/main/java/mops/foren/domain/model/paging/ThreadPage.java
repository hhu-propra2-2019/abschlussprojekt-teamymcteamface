package mops.foren.domain.model.paging;

import lombok.AllArgsConstructor;
import lombok.Data;
import mops.foren.domain.model.Thread;

import java.util.List;

@AllArgsConstructor
@Data
public class ThreadPage {
    Paging paging;
    List<Thread> threads;

    public Long getCountUnmoderatedPosts() {
        return threads.stream()
                .filter(thread -> thread.getUnModerated() != null)
                .mapToLong(Thread::getUnModerated).sum();
    }
}
