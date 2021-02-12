package moe.ksmz.rodentraid.sck.Service;

import java.io.FileNotFoundException;
import lombok.extern.slf4j.Slf4j;
import moe.ksmz.rodentraid.sck.Service.Contracts.Loadable;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class PostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        if (bean instanceof Loadable) {
            try {
                ((Loadable<?>) bean).loadEntries();
            } catch (FileNotFoundException e) {
                log.error("Failed to load entries for {}: {}", beanName, e.getMessage());
            }
        }

        return bean;
    }
}
