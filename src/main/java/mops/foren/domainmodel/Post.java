package mops.foren.domainmodel;

import java.sql.Date;
import java.util.List;

public class Post {
    User user;
    Date date;
    String text;
    List<Content> contentList;
}
