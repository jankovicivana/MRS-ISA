package ftn.mrs.isa.rentalapp.service;


import ftn.mrs.isa.rentalapp.model.user.Advertiser;
import ftn.mrs.isa.rentalapp.model.user.Client;
import ftn.mrs.isa.rentalapp.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Locale;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    /*
     * Koriscenje klase za ocitavanje vrednosti iz application.properties fajla
     */
    @Autowired
    private Environment env;

    /*
     * Anotacija za oznacavanje asinhronog zadatka
     * Vise informacija na: https://docs.spring.io/spring/docs/current/spring-framework-reference/integration.html#scheduling
     */
    @Async
    public void sendNotificationAsync(Client client, String entityName) throws MailException, InterruptedException, MessagingException {
        System.out.println("Slanje emaila...");
        MimeMessageHelper mail = new MimeMessageHelper(javaMailSender.createMimeMessage(), true, "UTF-8");
        mail.setTo(client.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));

        mail.setSubject("Rental app notification");
        mail.setText("<html>\n" +
                "    <body>\n" +
                "        <div style=\"margin: 50px;\">\n" +
                "            <div style=\"background-color: rgb(99, 216, 99);height: 55px;\">\n" +
                "                    <h1 style=\"margin-left:15px; color: white;\">Successful !</h1>\n" +
                "            </div>\n" +
                "            <div style=\"margin-top: 10px;\">\n" +
                "                <div style=\"margin: 25px;\">\n" +
                "                Dear "+client.getName()+",\n" +
                "                <br/>\n" +
                "                Your review of "+ entityName +" is acccepted.\n" +
                "                <br/>\n" +
                "                Regards,\n" +
                "                <br/>\n" +
                "                <span >Rental app team</span>\n" +
                "            </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "\n" +
                "    </body>\n" +
                "</html>",true);
        //mail.set
        javaMailSender.send( mail.getMimeMessage());
        System.out.println("Email poslat!");
    }

    @Async
    public void sendNotificationReportToClientAsync(Client client, Advertiser advertiser,String status) throws MailException, InterruptedException, MessagingException {
        System.out.println("Slanje emaila...");
        MimeMessageHelper mail = new MimeMessageHelper(javaMailSender.createMimeMessage(), true, "UTF-8");
        mail.setTo(client.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));

        mail.setSubject("Rental app notification");
        mail.setText("<html>\n" +
                "    <body>\n" +
                "        <div style=\"margin: 50px;\">\n" +
                "            <div style=\"background-color: rgb(135,206,250);height: 55px;\">\n" +
                "                    <h1 style=\"margin-left:15px; color: white;\">"+status.toUpperCase()+" </h1>\n" +
                "            </div>\n" +
                "            <div style=\"margin-top: 10px;\">\n" +
                "                <div style=\"margin: 25px;\">\n" +
                "                Dear "+client.getName()+",\n" +
                "                <br/>\n" +
                "                Penalty in reservation report which was given by "+advertiser.getName()+" "+ advertiser.getSurname()+" is "+status+"\n" +
                "                <br/>\n" +
                "                Regards,\n" +
                "                <br/>\n" +
                "                <span >Rental app team</span>\n" +
                "            </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "\n" +
                "    </body>\n" +
                "</html>",true);
        //mail.set
        javaMailSender.send( mail.getMimeMessage());
        System.out.println("Email poslat!");
    }
    @Async
    public void sendNotificationReportToAdvertiserAsync(Client client, Advertiser advertiser,String status) throws MailException, InterruptedException, MessagingException {
        System.out.println("Slanje emaila...");
        MimeMessageHelper mail = new MimeMessageHelper(javaMailSender.createMimeMessage(), true, "UTF-8");
        mail.setTo(advertiser.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));

        mail.setSubject("Rental app notification");
        mail.setText("<html>\n" +
                "    <body>\n" +
                "        <div style=\"margin: 50px;\">\n" +
                "            <div style=\"background-color: rgb(135,206,250);height: 55px;\">\n" +
                "                    <h1 style=\"margin-left:15px; color: white;\">"+status.toUpperCase()+" </h1>\n" +
                "            </div>\n" +
                "            <div style=\"margin-top: 10px;\">\n" +
                "                <div style=\"margin: 25px;\">\n" +
                "                Dear "+advertiser.getName()+",\n" +
                "                <br/>\n" +
                "                Penalty which you gave to "+client.getName()+" "+ client.getSurname()+" is "+status+"\n" +
                "                <br/>\n" +
                "                Regards,\n" +
                "                <br/>\n" +
                "                <span >Rental app team</span>\n" +
                "            </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "\n" +
                "    </body>\n" +
                "</html>",true);
        //mail.set
        javaMailSender.send( mail.getMimeMessage());
        System.out.println("Email poslat!");
    }

    @Async
    public void sendRegistrationRejected(Advertiser a, String reason) throws MessagingException {
        MimeMessageHelper mail = new MimeMessageHelper(javaMailSender.createMimeMessage(), true, "UTF-8");
        mail.setTo(a.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));

        mail.setSubject("Rental app notification");
        mail.setText("<html>\n" +
                "    <body>\n" +
                "        <div style=\"margin: 50px;\">\n" +
                "            <div style=\"background-color: rgb(135,206,250);height: 55px;\">\n" +
                "                    <h1 style=\"margin-left:15px; color: white;\">Rejected </h1>\n" +
                "            </div>\n" +
                "            <div style=\"margin-top: 10px;\">\n" +
                "                <div style=\"margin: 25px;\">\n" +
                "                Dear "+a.getName()+",\n" +
                "                <br/>\n" +
                "                Your registration is rejected. The reason is :"+reason.toLowerCase()+"\n" +
                "                <br/>\n" +
                "                Regards,\n" +
                "                <br/>\n" +
                "                <span >Rental app team</span>\n" +
                "            </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "\n" +
                "    </body>\n" +
                "</html>",true);
        //mail.set
        javaMailSender.send( mail.getMimeMessage());
        System.out.println("Email poslat!");
    }
    @Async
    public void sendDeletionProfileNotification(User a, String status) throws MessagingException {
        MimeMessageHelper mail = new MimeMessageHelper(javaMailSender.createMimeMessage(), true, "UTF-8");
        mail.setTo(a.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));

        mail.setSubject("Rental app notification");
        mail.setText("<html>\n" +
                "    <body>\n" +

                "        <div style=\"margin: 50px;\">\n" +
                "            <div style=\"background-color: rgb(135,206,250);height: 55px;\">\n" +
                "                    <h1 style=\"margin-left:15px; color: white;\">"+status.toUpperCase()+" </h1>\n" +
                "            </div>\n" +
                "            <div style=\"margin-top: 10px;\">\n" +
                "                <div style=\"margin: 25px;\">\n" +
                "                Dear "+a.getName()+",\n" +
                "                <br/>\n" +
                "                Your request for deleting account is "+status+"\n" +
                "                <br/>\n" +
                "                Regards,\n" +
                "                <br/>\n" +
                "                <span >Rental app team</span>\n" +
                "            </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "\n" +
                "    </body>\n" +
                "</html>",true);
        javaMailSender.send( mail.getMimeMessage());
        System.out.println("Email poslat!");
    }

    @Async
    public void sendRegistrationAccepted(Advertiser a) throws MessagingException {
        MimeMessageHelper mail = new MimeMessageHelper(javaMailSender.createMimeMessage(), true, "UTF-8");
        mail.setTo(a.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));

        mail.setSubject("Rental app notification");
        mail.setText("<html>\n" +
                "    <body>\n" +
                "        <div style=\"margin: 50px;\">\n" +
                "            <div style=\"background-color: rgb(99, 216, 99);height: 55px;\">\n" +
                "                    <h1 style=\"margin-left:15px; color: white;\">Successful </h1>\n" +
                "            </div>\n" +
                "            <div style=\"margin-top: 10px;\">\n" +
                "                <div style=\"margin: 25px;\">\n" +
                "                Dear "+a.getName()+",\n" +
                "                <br/>\n" +
                "                Your registration request is accepted\n" +
                "                <br/>\n" +
                "                Regards,\n" +
                "                <br/>\n" +
                "                <span >Rental app team</span>\n" +
                "            </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "\n" +
                "    </body>\n" +
                "</html>",true);
        //mail.set
        javaMailSender.send( mail.getMimeMessage());
        System.out.println("Email poslat!");
    }
}
