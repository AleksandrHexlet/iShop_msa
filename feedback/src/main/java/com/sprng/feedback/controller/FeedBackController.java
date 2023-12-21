package com.sprng.feedback.controller;

import com.sprng.feedback.model.Complaint;
import com.sprng.feedback.model.FeedBack;
import com.sprng.feedback.service.FeedBackService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/feedback")
@Validated
public class FeedBackController {


    private FeedBackService feedBackService;

    @Autowired
    public FeedBackController(FeedBackService feedBackService) {
        this.feedBackService = feedBackService;
    }

    @Secured("ROLE_CUSTOMER")
    @PostMapping
    public Mono<FeedBack> createFeedBack (@RequestBody @Valid FeedBack feedBack) {
        Mono<FeedBack> newFeedBack = feedBackService.createFeedBack(feedBack);
        return newFeedBack;
    }


    @GetMapping
    public Flux<FeedBack> getFeedBacks(@RequestParam @Positive int productID) {
        return feedBackService.getFeedBacksByProductID(productID);
    }

    //
    @Secured("ROLE_ADMIN")
    @PutMapping
    public Mono<FeedBack> deleteFeedBack(@RequestParam @Positive int feedBackID) {
        Mono<FeedBack> newFeedBack = feedBackService.deleteFeedBackById(feedBackID)
                .doOnError((throwable) -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, throwable.getMessage());
                });
        return newFeedBack;
    }

    @Secured("ROLE_CUSTOMER")
    @PostMapping("/complaint")
    public ResponseEntity<Void> leaveComplaint(@Valid Complaint complaint) {
        feedBackService.leaveComplaint(complaint)
                .doOnError((throwable) -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, throwable.getMessage());
                });
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Secured({"ROLE_ADMIN", "ROLE_READONLY_ADMIN"})
    @GetMapping("/compliant")
    public Flux<Complaint> getUnprocessedComplaint(){
        return feedBackService.getUnprocessedComplaint();
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/compliant")
    public Mono<String> completeComplaintProcessing(@RequestParam @Positive int compliantId ){
        return feedBackService.completeComplaintProcessing(compliantId);
    }


    @Secured("ROLE_ADMIN")
    @PutMapping("/compliant/count")
    public Mono<Integer> complaintCount(@RequestParam @Positive int ProductTraderId ){
        return feedBackService.complaintCount(ProductTraderId);
        // возвращаем количество жалоб, когда админ переходить внутрь жалобы и начинает работать с ней и ему
        // надо принять решение понижать рейтинг продавцап или нет. Используй countByProductTraderId в
        // compliantRepository
    }
}