package guru.qa.niffler.jupiter.extension;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterResolver;


import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.model.UserJson;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;

import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Stream;

import static guru.qa.niffler.model.UserJson.simpleUser;

public class UsersQueueExtension implements
        BeforeEachCallback,
        AfterEachCallback,
        ParameterResolver {
    public static final ExtensionContext.Namespace NAMESPACE
            = ExtensionContext.Namespace.create(UsersQueueExtension.class);

    public static final Map<User.Selector, Queue<UserJson>> USERS = new ConcurrentHashMap<>();

    static {
        USERS.put(User.Selector.INVITATION_SEND, new ConcurrentLinkedQueue<>(
                List.of(simpleUser("duck", "12345"))
        ));
        USERS.put(User.Selector.INVITATION_RECEIVED, new ConcurrentLinkedQueue<>(
                List.of(simpleUser("barsik", "12345"))
        ));
        USERS.put(User.Selector.WITH_FRIENDS, new ConcurrentLinkedQueue<>(
                List.of(simpleUser("dima", "12345"))
        ));
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {

        //получение тестового метода @Test
        Method testMethod = context.getRequiredTestMethod();

        //получение beforeEach методов
        List<Method> beforeMethods = Arrays.stream(
                        context.getRequiredTestClass().getDeclaredMethods())
                .filter(q -> q.isAnnotationPresent(BeforeEach.class)).toList();

        List<Method> methods = new ArrayList<>();
        methods.add(testMethod);
        methods.addAll(beforeMethods);

        List<Parameter> parameters = methods.stream().flatMap(w -> Arrays.stream(w.getParameters()))
                .filter(e -> e.isAnnotationPresent(User.class)).toList();

        Map<User.Selector, UserJson> users = new HashMap<>();

        for (Parameter parameter : parameters) {
            User.Selector selector = parameter.getAnnotation(User.class).selector();
            if (users.containsKey(selector)) continue;
            UserJson userForTest = null;
            Queue<UserJson> queue = USERS.get(selector);
            while (userForTest == null) {
                userForTest = queue.poll();
            }
            users.put(selector, userForTest);
        }

        Allure.getLifecycle().updateTestCase(testCase -> {
            testCase.setStart(new Date().getTime());
        });
        context.getStore(NAMESPACE).put(context.getUniqueId(), users);
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        Map<User.Selector, UserJson> user = context.getStore(NAMESPACE).get(context.getUniqueId(), Map.class);
        user.forEach((selector, userJson) -> USERS.get(selector).add(userJson));

    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Parameter parameter = parameterContext.getParameter();
        boolean isUserJson = parameter.getType().isAssignableFrom(UserJson.class);
        boolean hasUserAnnotation = parameter.isAnnotationPresent(User.class);
        return isUserJson ? (hasUserAnnotation ? true : false) : false;
    }

    @Override
    public UserJson resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        User.Selector selector = parameterContext.findAnnotation(User.class).get().selector();
        ExtensionContext.Store store = extensionContext.getStore(NAMESPACE);
        Map<User.Selector, UserJson> userMap = store.get(extensionContext.getUniqueId(), Map.class);
        return userMap.get(selector);
    }
}
