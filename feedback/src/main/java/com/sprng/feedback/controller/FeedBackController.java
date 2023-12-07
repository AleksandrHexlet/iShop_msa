package com.sprng.feedback.controller;

import com.sprng.feedback.model.FeedBack;
import com.sprng.feedback.service.FeedBackService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PostMapping
    public Mono<FeedBack> getFeedBack(@RequestBody @Valid FeedBack feedBack) {
        Mono<FeedBack> newFeedBack = feedBackService.createFeedBack(feedBack);
        return newFeedBack;
    }



    @GetMapping
    public Flux<FeedBack> getFeedBacks(@RequestParam @Positive int productID) {
        return feedBackService.getFeedBacksByProductID(productID);
    }
//
    @PutMapping
    public Mono<FeedBack> deleteFeedBack(@RequestParam @Positive int feedBackID) {
        Mono<FeedBack> newFeedBack = feedBackService.deleteFeedBackById(feedBackID)
                .doOnError((throwable)->{
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, throwable.getMessage());
                });
        return newFeedBack;
    }

}
