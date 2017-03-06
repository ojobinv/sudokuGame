
package dev.ovj.solver.controller;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Application subclass which adds the entry point to the application and
 * for adding resources and providers
 * Since no explicit adding is done, it will get all the resources  
 * same package.
 * @author ovj
 */
@ApplicationPath("/game")
public class MainController extends Application{
    
}
