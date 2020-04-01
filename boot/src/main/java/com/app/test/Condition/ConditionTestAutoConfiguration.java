package com.app.test.Condition;

import com.app.test.yaml.Dog;
import com.app.test.yaml.Person;
import com.app.test.yaml.PersonProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author:wuqi
 * @date:2020/3/25
 * @description:com.app.test.Condition
 * @version:1.0
 */
@Configuration
@ConditionalOnClass({Person.class, Dog.class})
@EnableConfigurationProperties(PersonProperty.class)
public class ConditionTestAutoConfiguration {

    @Bean
    Person person(PersonProperty personProperty){
        Person person = new Person();
        person.setPersonProperty(personProperty);
        return person;
    }

}
