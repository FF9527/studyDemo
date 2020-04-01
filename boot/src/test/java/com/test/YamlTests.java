package com.test;

import com.app.App;
import com.app.test.yaml.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author:wuqi
 * @date:2020/3/24
 * @description:com.test
 * @version:1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class YamlTests {

    @Autowired
    private Person person;

    @Test
    public void yamlTest(){
        System.out.println(person);
    }
}
