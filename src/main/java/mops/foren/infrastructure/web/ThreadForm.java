package mops.foren.infrastructure.web;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class ThreadForm {
    private String title;
    private String content;

}
