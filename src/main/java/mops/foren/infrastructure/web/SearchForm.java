package mops.foren.infrastructure.web;

import lombok.Value;
import mops.foren.domain.model.Post;
import mops.foren.domain.model.ThreadId;
import mops.foren.domain.model.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static mops.foren.infrastructure.web.ValidationService.MAX_CONTENT_LENGTH;
import static mops.foren.infrastructure.web.ValidationService.MIN_CONTENT_LENGTH;

@Value
public class SearchForm {

    private final String searchContent;
}
