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
    public static final File FROM_FOLDER = new File("temp/upload/");
    private FtpConfig ftpConfig;

    public Folder2FtpRouteBuilder(FtpConfig ftpConfig) {
        this.ftpConfig = ftpConfig;
    }

    @Override
    public void configure() throws Exception {
        String fileFrom = getFromFolder();
        LOGGER.debug("Folder2FtpRouteBuilder From: {}", fileFrom);
        LOGGER.debug("Folder2FtpRouteBuilder To: {}", getToFtp());

        from(fileFrom).process(new Processor() {
            @Override
            public void process(Exchange exchng) throws Exception {
                File f = exchng.getIn().getBody(File.class);
                LOGGER.debug("Folder2FtpRouteBuilder processing: " + f.getName());
            }
        }).to(getToFtp());
    }

    private String getFromFolder() {
        return "file://" + FROM_FOLDER.getAbsolutePath();
    }

    private String getToFtp() {
        StringBuilder sb = new StringBuilder();
        sb.append(ftpConfig.getType());
        sb.append("://");
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