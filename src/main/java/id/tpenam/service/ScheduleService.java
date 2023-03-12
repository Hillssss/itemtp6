package id.tpenam.service;

import com.cronutils.model.field.expression.Every;
import io.quarkus.scheduler.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.time.LocalDateTime;

@ApplicationScoped
public class ScheduleService {
    @Inject
    MailService mailService;
    Logger logger = LoggerFactory.getLogger(ScheduleService.class);
    @Scheduled(every = "10s")
    public void  generateItem(){
        logger.info("ItemAttribut_{}", LocalDateTime.now());
    }
    @Scheduled(cron = "* 53 20 12 * ? *")
    public void generateEmail() throws IOException {
        mailService.sendExcelToEmail("samaranhilal@gmail.com");
        logger.info("SEND EMAIL SUKSES");
    }
}

