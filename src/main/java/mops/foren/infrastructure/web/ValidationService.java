package mops.foren.infrastructure.web;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

@Service
public class ValidationService {
    public static final int MAX_TITLE_LENGTH = 40;
    public static final int MAX_DESCRIPTION_LENGTH = 100;
    public static final int MAX_CONTENT_LENGTH = 250;

    public static final int MIN_TITLE_LENGTH = 3;
    public static final int MIN_DESCRIPTION_LENGTH = 3;
    public static final int MIN_CONTENT_LENGTH = 3;

    /**
     * Generates a report string from the binding result of a failed validation.
     *
     * @param bindingResult result of failed binding
     * @return string description of failed bindings
     */
    public String getErrorDescriptionFromErrorObjects(BindingResult bindingResult) {
        // make list of all failed bindings
        List<ObjectError> errors = bindingResult.getAllErrors();

        String errorMessage = "Fehlerhaft: ";

        // for every failed binding
        for (int i = 0; i < errors.size(); i++) {
            if (i > 0) {
                errorMessage += " & ";
            }
            // get description error code
            String errorCode = errors.get(i).getCodes()[1];

            // extract and append field name
            errorMessage += errorCode.split("\\.")[1];
        }
        return errorMessage;
    }

}
