package com.app;

import com.alibaba.fastjson.JSONObject;
import com.app.test.yaml.Dog;
import com.app.test.yaml.Person;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void toJson(){
        Person person = new Person();
        person.setUserId(9527L);
        person.setUserName("tangbohu");
        person.setBoss(true);
        person.setBirth(new Date());
        Map<String, Object> map = new HashMap<>();
        map.put("girlFriend","qiuxiang");
        person.setMap(map);
        List<String> list = new ArrayList<>();
        list.add("qin");
        list.add("qi");
        list.add("shu");
        list.add("hua");
        person.setList(list);
        Dog dog = new Dog();
        dog.setDogName("wangcai");
        dog.setAge(1);
        person.setDog(dog);
        System.out.println(JSONObject.toJSON(person));
    }
}
