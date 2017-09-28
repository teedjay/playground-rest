package com.teedjay.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Set the default JAX-RS application config that also specifies the entry path of the url.
 * Defaults to scan thru class path and deploy all found resources under this application path.
 *
 * It is possible to have multiple ApplicationPath's and also possible to explicitly say
 * which REST resources to deploy by overriding Application methods.  This is handy if you
 * need to expose a REST resource on multiple paths.
 *
 * @author thore johnsen
 */
@ApplicationPath("rest")
public class JAXRSConfig extends Application {
    
}
