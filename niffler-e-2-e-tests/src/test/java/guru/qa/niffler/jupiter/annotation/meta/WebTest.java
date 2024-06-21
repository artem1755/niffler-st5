package guru.qa.niffler.jupiter.annotation.meta;

import guru.qa.niffler.jupiter.extension.BrowserExtension;
import guru.qa.niffler.jupiter.extension.CategoryExtension;
import guru.qa.niffler.jupiter.extension.SpendExtension;
import guru.qa.niffler.jupiter.extension.UsersQueueExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//@Retention(RetentionPolicy.RUNTIME)
//@Target(ElementType.TYPE)
//@ExtendWith({
//        BrowserExtension.class,
//        CategoryExtension.class,
//        SpendExtension.class
//})
//public @interface WebTest {
//}


// что бы прочитать аннотацию в runtime
@Retention(RetentionPolicy.RUNTIME)
// Разрешение на место в коде куда её можно вставить - над классом
@Target(ElementType.TYPE)
@ExtendWith({CategoryExtension.class, SpendExtension.class, BrowserExtension.class, UsersQueueExtension.class})
public @interface WebTest {
}