package mops.foren;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.annotation.SessionScope;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;

@AnalyzeClasses(packages = "mops.foren")
public class ArchUnitTest {

    @ArchTest
    public static final ArchRule ONION_ARCHITECTURE = onionArchitecture()
            .domainModels("..mops.foren.domain.model..")
            .domainServices("..mops.foren.domain..")
            .applicationServices("..mops.foren.applicationservices..")
            .adapter("web", "..mops.foren.infrastructure.web..")
            .adapter("persistence", "..mops.foren.infrastructure.persistence..");

    @ArchTest
    static final ArchRule CONTROLLER_CAN_HANDLE_MULTIPLE_USER = classes()
            .that()
            .areAnnotatedWith(Controller.class)
            .should()
            .beAnnotatedWith(SessionScope.class);

    @ArchTest
    static final ArchRule ALL_CONTROLLERS_LISTEN_TO_RIGHT_PATH = classes()
            .that()
            .areAnnotatedWith(Controller.class)
            .should()
            .beAnnotatedWith(RequestMapping.class)
            .andShould(new ArchCondition<>("have annotation value") {
                @Override
                public void check(JavaClass item, ConditionEvents events) {
                    RequestMapping annotation = item.getAnnotationOfType(RequestMapping.class);
                    if (annotation.value()[0].startsWith("/foren")
                            && !annotation.value()[0].isEmpty()) {
                        return;
                    } else {
                        events.add(SimpleConditionEvent
                                .violated(item,
                                        "Annotation should be ‘@RequestMapping(“/foren/...”)’"));
                    }


                }
            });

}
