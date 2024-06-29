package guru.qa.niffler.jupiter.annotation.meta;


import guru.qa.niffler.jupiter.extension.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
// Разрешение на место в коде куда её можно вставить - над классом
@Target(ElementType.TYPE)
@ExtendWith({CategoryHttpExtension.class, SpendHttpExtension.class, BrowserExtension.class})
public @interface WebTestHttp {
}


