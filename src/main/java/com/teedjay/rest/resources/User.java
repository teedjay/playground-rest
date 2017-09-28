package com.teedjay.rest.resources;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This is an example data structure used as parameters in the REST interface to get automatic
 * handling of XML and JSON marshalling / parsing as well as beans validation enforcement.
 *
 * It needs to have an empty constructor to allow for binding.
 *
 * @author thore johnsen
 */
@XmlRootElement
public class User {

    @NotNull
    @Size(min = 3)
    public String name;

    @NotNull
    @Min(value = 0, message = "the value must be positive")
    public Integer age;

    @NotNull
    @Size(max = 10)
    public String address;

    public String text;

    public User() {
        /* empty constructor needed by jaxb and json */
    }

    public User(String name, Integer age, String address, String text) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.text = text;
    }

    // just for easy debugging and logging purposes
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

}
