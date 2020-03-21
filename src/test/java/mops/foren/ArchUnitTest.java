package mops.foren;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packages = "mops.foren")
public class ArchUnitTest {
    @ArchTest
    static final ArchRule controllerCanHandleMultipleUser = classes()
            .that()
            .areAnnotatedWith(Controller.class)
            .should()
            .beAnnotatedWith(SessionScope.class);
}