package hu.arnoldfarkas.pot.service.camel;

import hu.arnoldfarkas.pot.service.FtpConfig;
import java.io.File;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Folder2FtpRouteBuilder extends RouteBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(Folder2FtpRouteBuilder.class);
    private static final File FROM_FOLDER = new File("temp/upload/");
    private int counter = 0;
    private FtpConfig ftpConfig;

    public Folder2FtpRouteBuilder(FtpConfig ftpConfig) {
        this.ftpConfig = ftpConfig;
    }

    @Override
    public void configure() throws Exception {
        LOGGER.debug("Folder2FtpRouteBuilder From: {}", FROM_FOLDER.getAbsolutePath());
        LOGGER.debug("Folder2FtpRouteBuilder To: {}", getToFtp());

        from(FROM_FOLDER.getAbsolutePath()).process(new Processor() {
            @Override
            public void process(Exchange exchng) throws Exception {
                counter++;
                LOGGER.debug("Folder2FtpRouteBuilder processed: " + counter);
            }
        }).to(getToFtp());
    }

    private String getToFtp() {
        StringBuilder sb = new StringBuilder();
        sb.append("sftp://");
        sb.append(ftpConfig.getUsername());
        sb.append("@");
        sb.append(ftpConfig.getHost());
        sb.append(":");
        sb.append(ftpConfig.getPort());
        sb.append(ftpConfig.getPath());
        sb.append("?password=");
        sb.append(ftpConfig.getPassword());
        return sb.toString();
    }
}