package com.sprng.feedback.service;

import com.sprng.feedback.model.Complaint;
import com.sprng.feedback.model.FeedBack;
import com.sprng.feedback.repository.CompliantRepository;
import com.sprng.feedback.repository.FeedBackRepository;
import com.sprng.library.exception.IshopResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;

@Service
public class FeedBackService {
    private FeedBackRepository feedBackRepository;
    private ClientCustomerAndTraderService clientCustomerAndTraderService;
    private CompliantRepository compliantRepository;

    public FeedBackService(FeedBackRepository feedBackRepository,
                           ClientCustomerAndTraderService clientCustomerAndTraderService,
                           CompliantRepository compliantRepository) {
        this.feedBackRepository = feedBackRepository;
        this.clientCustomerAndTraderService = clientCustomerAndTraderService;
        this.compliantRepository = compliantRepository;
    }

    public Mono<FeedBack> createFeedBack(FeedBack feedBack) {
//        Проверяем есть ли такой customer по customerID.
//        Для этого отправляем запрос через тип WebClient на сервис users. У users должен
//        быть контроллер который нам ответить есть такой кастомер в БД или нет

//        Проверяем есть ли такой product по ProductID.
//        Для этого отправляем запрос через тип WebClient на сервис users. У users должен
//        быть контроллер который нам ответить есть такой кастомер в БД или нет
//
        return null;
    }

    public Flux<FeedBack> getFeedBacksByProductID(int productID) {
        return feedBackRepository.findAllByProductIDAndIsDeleteFalse(productID);
    }

    public Mono<FeedBack> deleteFeedBackById(int feedBackID) {
        Mono<FeedBack> feedBack = feedBackRepository.findById(feedBackID)
                .flatMap((feedback) -> {
                    if (feedback == null)
                        return Mono.error(new IshopResponseException("Отзыв по указанному id не найден "));
                    feedback.delete();
                    return feedBackRepository.save(feedback);
                });
        return feedBack;
    }


    public Mono<Complaint> leaveComplaint(Complaint complaint) {
        clientCustomerAndTraderService
                .existsByCustomerAndProductTrader(complaint.getCustomerId(),
                        complaint.getProductTraderId()).flatMap((booleanValue) -> {
                    if (booleanValue) {
                        complaint.setCreatedAt(ZonedDateTime.now());
                       return compliantRepository.save(complaint);
                    } else {
                     return Mono.error(new IshopResponseException("Customer or Trader does not exist"));
                    }
                });
        return null;

    }

    public Flux<Complaint> getUnprocessedComplaint() {

       return compliantRepository.findAllByStatusComplaintFalseOrderByCreatedAtDesc();
    }

    public Mono<String> completeComplaintProcessing(int compliantId) {
       return compliantRepository.findById(compliantId)
               .flatMap((compliant)-> {
                   compliant.setStatusComplaint(true);
                   compliantRepository.save(compliant);
                   return Mono.just("Success");
               });
    }

    public Mono<Integer> complaintCount(int productTraderId) {
      return compliantRepository.countByProductTraderId(productTraderId);
    }
}


