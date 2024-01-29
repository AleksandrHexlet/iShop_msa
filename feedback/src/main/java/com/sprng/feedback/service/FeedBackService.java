package com.sprng.feedback.service;

import com.sprng.feedback.model.Complaint;
import com.sprng.feedback.model.FeedBack;
import com.sprng.feedback.repository.CompliantRepository;
import com.sprng.feedback.repository.FeedBackRepository;
import com.sprng.library.exception.IshopResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
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

//        Mono<FeedBack> feedBack1 = feedBackRepository.findById(feedBackID)
//                .mergeWith(feedBackRepository.findById(feedBackID123))
//                .reduce((feedBackAccumulator, feedBack2)->{
//                    FeedBack f3 = new FeedBack();
//                    f3.setProductID(feedBackAccumulator.getProductID());
//                    f3.setText(feedBack2.getText());
//                    return f3;
//                    //return Mono<Feedback>
//                })
//                .zipWith(Mono.just(33),(feedBack123,integer33)-> {
//                 feedBack123.setText(String.valueOf(integer33));
//                 return feedBack123;
//                })
//                .mergeWith(Mono.just(new FeedBack("qw",123,456)))
//                .reduce((accum, feedBack12345)-> {
//                    accum.setText(feedBack12345.getText());
//                    return accum;
//                });

//        return feedBack;
    };
//Mono<List<Complaint>>
//Flux<Complaint>
//    flatMap


    public Mono<Complaint> leaveComplaint(Complaint complaint, JwtAuthenticationToken authenticationToken) {
        String tokenValue = authenticationToken.getToken().getTokenValue();
        clientCustomerAndTraderService
                .existsByCustomerAndProductTrader(complaint.getCustomerId(),
                        complaint.getProductTraderId(),tokenValue).flatMap((booleanValue) -> {
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


