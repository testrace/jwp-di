package core.di.factory;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import core.annotation.Configuration;
import core.mvc.tobe.HandlerExecution;
import core.mvc.tobe.HandlerKey;
import core.util.ReflectionUtils;
import java.util.Map;
import java.util.Set;
import javax.sql.DataSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

class ConfigurationBeanScannerTest {

    @DisplayName("`@Configuration` 애너테이션이 적용된 클래스를 를 주입받아 ConfigurationBeanScanner 를 생성한다")
    @Test
    void constructor() {
        //given
        final Set<Class<?>> configurationClasses = ReflectionUtils.getTypesAnnotatedWith(new Reflections("core.di.factory.example"), Configuration.class);

        final ConfigurationBeanScanner actual = new ConfigurationBeanScanner(configurationClasses);
        //when

        //then
        assertThat(actual).isEqualTo(new ConfigurationBeanScanner(configurationClasses));

    }

    @DisplayName("`@Bean` 애너테이션이 적용된 메서드를 반환한다")
    @Test
    void scan_beans_annotation() {
        //given
        final Set<Class<?>> configurationClasses = ReflectionUtils.getTypesAnnotatedWith(new Reflections("core.di.factory.example"), Configuration.class);

        final ConfigurationBeanScanner configurationBeanScanner = new ConfigurationBeanScanner(configurationClasses);

        //when
        final BeanFactory beanFactory = new BeanFactory();
        configurationBeanScanner.scan(beanFactory);

        //then
        final DataSource bean = beanFactory.getBean(DataSource.class);
        assertThat(bean).isNotNull();

    }
}
