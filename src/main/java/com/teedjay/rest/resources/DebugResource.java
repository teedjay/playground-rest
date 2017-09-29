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
import java.time.LocalDateTime;
import java.util.Set;
import java.util.StringJoiner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * REST function that returns all CDI beans registered in the system.
 * This will log using standard JUL and end up in the payara system log.
 *
 * Default for Payara Micro is console, use command line option for file,
 * "--logToFile ./PayaraMicro.log"
 *
 * @author thore johnsen
 */
@Path("debug")
@Produces(MediaType.APPLICATION_XML)
@RequestScoped
public class DebugResource {

    private static final Logger log = Logger.getLogger(DebugResource.class.getName());

    @Inject
    BeanManager beanManager;

    @GET
    public String listAllBeans() {
        log.log(Level.INFO, "This is JUL logging listing all beans at {0}", LocalDateTime.now());
        Set<Bean<?>> beans = beanManager.getBeans(Object.class, new AnnotationLiteral<Any>() {});
        StringJoiner sj = new StringJoiner(",\n", "<cdi>\n", "\n</cdi>\n");
        beans.stream().forEach(b -> sj.add(b.getBeanClass().getName()));
        return sj.toString();
    }

}
