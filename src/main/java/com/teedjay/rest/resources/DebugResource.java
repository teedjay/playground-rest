package com.teedjay.rest.resources;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Set;
import java.util.StringJoiner;

/**
 * REST function that returns all CDI beans registered in the system.
 *
 * @author thore johnsen
 */
@Path("debug")
@Produces(MediaType.APPLICATION_XML)
@RequestScoped
public class DebugResource {

    @Inject
    BeanManager beanManager;

    @GET
    public String listAllBeans() {
        Set<Bean<?>> beans = beanManager.getBeans(Object.class, new AnnotationLiteral<Any>() {});
        StringJoiner sj = new StringJoiner(",\n", "<cdi>\n", "\n</cdi>\n");
        beans.stream().forEach(b -> sj.add(b.getBeanClass().getName()));
        return sj.toString();
    }

}
