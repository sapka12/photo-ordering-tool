package hu.arnoldfarkas.pot.service.camel;

import java.io.File;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class MovingFromFolderRouteBuilder extends RouteBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovingFromFolderRouteBuilder.class);
    public static final File FROM_FOLDER = new File("temp/upload/");

    @Value("#{camel['routes']}")
    private String routes;
    
    @Override
    public void configure() throws Exception {
        String[] camelRoutes = getCamelRoutes();
        Assert.notEmpty(camelRoutes);
        
        RouteDefinition routeDefinition = from(getFromFolder());
        for (String route : camelRoutes) {
            routeDefinition = routeDefinition.to(route);
        }
    }

    private String getFromFolder() {
        return "file://" + FROM_FOLDER.getAbsolutePath();
    }

    private String[] getCamelRoutes() {
        Assert.notNull(routes);
        return routes.split(",");
    }

}
