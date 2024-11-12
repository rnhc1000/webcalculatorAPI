package br.dev.ferreiras.webcalculatorapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.ZonedDateTime;

/**
 * Goal: Provide endpoints to make math calculations
 */
@SpringBootApplication
public class WebcalculatorapiApplication implements CommandLineRunner {

    /**
     * Log messages on console to indicate the application has started successfully!
     */
    public static final Logger logger = LoggerFactory.getLogger(WebcalculatorapiApplication.class);

    /**
     * Entry point java App execution
     *
     * @param args no args
     */
    public static void main(String[] args) {
        SpringApplication.run(WebcalculatorapiApplication.class, args);
    }

    /**
     * Indicates starting time, zone and Java version
     *
     * @param args no args
     * @throws Exception in case of failure getting the info for observability purposes
     */
    @Override
    public void run(String... args) throws Exception {

        final ZonedDateTime zonedDateTime = ZonedDateTime.now(ZonedDateTime.now().getZone());
        final String javaVersion = System.getProperty("java.version");
        final int cores = Runtime.getRuntime().availableProcessors();


        if (WebcalculatorapiApplication.logger.isInfoEnabled()) {
            logger.info("Calculator Web started running at zone {}, " +
                            "powered by java version {}, " +
                            "on top of {} cores",
                    zonedDateTime,
                    javaVersion,
                    cores
            );
        }

    }
}
