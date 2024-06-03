package com.spring.jwt.service;

import com.spring.jwt.Interfaces.BiddingTimerService;
import com.spring.jwt.dto.BiddingTimerRequestDTO;
import com.spring.jwt.entity.BiddingTimerRequest;
import com.spring.jwt.repository.BiddingTImerRepo;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BiddingTimerServiceImpl implements BiddingTimerService {

    private final BiddingTImerRepo biddingTImerRepo;

    private final ModelMapper modelMapper;

    private final JavaMailSender javaMailSender;

    private final Logger logger = LoggerFactory.getLogger(BiddingTimerServiceImpl.class);



    @Override
    public BiddingTimerRequestDTO startTimer(BiddingTimerRequestDTO biddingTimerRequest) {

        BiddingTimerRequest biddingTimerRequest1 = convertToEntity(biddingTimerRequest);
        BiddingTimerRequest save = biddingTImerRepo.save(biddingTimerRequest1);
        BiddingTimerRequestDTO biddingTimerRequestDTO = convertToDto(save);
        return biddingTimerRequestDTO;
    }

//    @Override
//    public void sendNotification(String recipient, String message) {
//
//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.setTo(recipient);
//        mailMessage.setSubject("Bidding Timer Notification");
//        mailMessage.setText(message);
//        javaMailSender.send(mailMessage);
//    }

    public void sendBulkEmails(List<String> recipients, String message) {
        try {
            int batchSize = 50;
            MimeMessage[] messages = new MimeMessage[batchSize];
            int messageIndex = 0;

            for (String recipient : recipients) {
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                helper.setTo(recipient);
                helper.setSubject("Bidding Timer Notification");
                helper.setText(message);

                messages[messageIndex++] = mimeMessage;

                if (messageIndex == batchSize) {
                    javaMailSender.send(messages);
                    messageIndex = 0;
                }
            }

            if (messageIndex > 0) {
                MimeMessage[] lastMessages = new MimeMessage[messageIndex];
                System.arraycopy(messages, 0, lastMessages, 0, messageIndex);
                javaMailSender.send(lastMessages);
            }
        } catch (Exception e) {
            logger.error("Failed to send bulk emails.", e);
        }
    }

    public BiddingTimerRequest convertToEntity(BiddingTimerRequestDTO biddingTimerRequestDTO){
        BiddingTimerRequest biddingtime = modelMapper.map(biddingTimerRequestDTO, BiddingTimerRequest.class);
        return biddingtime;
    }

    public BiddingTimerRequestDTO convertToDto (BiddingTimerRequest biddingTimerRequest) {
        BiddingTimerRequestDTO biddingtimeDto = modelMapper.map(biddingTimerRequest, BiddingTimerRequestDTO.class);
        return biddingtimeDto;
    }
}
