package com.app.test.yaml;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author:wuqi
 * @date:2020/3/24
 * @description:com.app.test.yaml
 * @version:1.0
 */
@ConfigurationProperties(prefix = "personproperty")
public class PersonProperty {

    private Long userId;
    private String userName;
    private Boolean boss;
    private Date birth;

    private Map<String,Object> map;
    private List<String> list;
    private Dog dog;

    @Override
    public String toString() {
        return "Person{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", boss=" + boss +
                ", birth=" + birth +
                ", map=" + map +
                ", list=" + list +
                ", dog=" + dog +
                '}';
    }

    public Boolean getBoss() {
        return boss;
    }

    public void setBoss(Boolean boss) {
        this.boss = boss;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
