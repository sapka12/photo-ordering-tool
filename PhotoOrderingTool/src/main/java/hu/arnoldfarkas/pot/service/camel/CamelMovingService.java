package hu.arnoldfarkas.pot.service.camel;

import hu.arnoldfarkas.pot.service.FtpConfig;
import hu.arnoldfarkas.pot.service.MovingService;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CamelMovingService implements MovingService, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(CamelMovingService.class);
    private RouteBuilder routeBuilder;
    private CamelContext context;
    @Autowired
    private FtpConfig ftpConfig;

    @Override
    public void afterPropertiesSet() throws Exception {
        routeBuilder = new Folder2FtpRouteBuilder(ftpConfig);
        context = new DefaultCamelContext();
        try {
            LOGGER.debug("Adding route...");
            context.addRoutes(routeBuilder);
            LOGGER.debug("Route added.");
        } catch (Exception ex) {
            LOGGER.error("error on camel config", ex);
        }
    }

    @Override
    public void start() {
        LOGGER.debug("CamelContext starting...");
        try {
            context.start();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        LOGGER.debug("CamelContext started.");
    }

    @Override
    public void stop() {
        LOGGER.debug("CamelContext stoping...");
        try {
            context.stop();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        LOGGER.debug("CamelContext stopped.");
    }
}
