package moe.ksmz.rodentraid.sck.Service;

import moe.ksmz.rodentraid.sck.Service.Contracts.Loadable;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        if (bean instanceof Loadable) {
            ((Loadable<?>) bean).loadEntries();
        }

        return bean;
    }
}
