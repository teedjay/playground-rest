package com.teedjay.rest;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This is an example data structure used as parameters in the REST interface to get automatic
 * handling of XML and JSON marshalling and parsing as well as beans validation enforcement.
 *
 *  @author thore johnsen
 */
@XmlRootElement
public class User {

    @Size(min = 3)
    public String name;

    public Integer age;

    public String address;

}
