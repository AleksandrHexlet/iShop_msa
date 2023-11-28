package com.sprng.library.service;

import org.springframework.stereotype.Service;

@Service
public class RandomGeneration {

 public String generate(Generation generation){
     return  generation.execute();
 }
}
