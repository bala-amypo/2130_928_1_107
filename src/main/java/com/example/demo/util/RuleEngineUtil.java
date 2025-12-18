package com.example.demo.util;
import com.example.demo.model.ClaimRule;
import java.util.List;
public class RuleEngineUtil{
    public static int evaluate(String text,List<ClaimRule>rules){
        int scores=0;
        for(ClaimRule rule:rules){
            if(text.toLowerCase().contains(rule.getKeyword().toLowerCase())){
                score+=rule.getWeight();
            }
        }
        return score;
    }
}