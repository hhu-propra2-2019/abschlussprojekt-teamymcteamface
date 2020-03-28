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


    /**
     * This method count the Posts that are not visible form the thread page.
     *
     * @return A count of all invisible posts.
     */
    public Long getCountUnmoderatedPosts() {
        return threads.stream()
                .filter(thread -> thread.getUnModerated() != null)
                .mapToLong(Thread::getUnModerated).sum();
    }
}
