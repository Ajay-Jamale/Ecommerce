package com.ajay.ai.controllers;

import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ajay.ai.services.AiChatBotService;
import com.ajay.model.User;
import com.ajay.request.Prompt;
import com.ajay.response.ApiResponse;
import com.ajay.service.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ai/chat")
public class AiChatBotController {

    private final AiChatBotService aiChatBotService;
    private final UserService userService;

    @PostMapping()
    public ResponseEntity<ApiResponse> generate(
            @RequestBody Prompt prompt,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long productId,
            @RequestHeader(required = false,name = "Authorization")String jwt) throws Exception {

        String message = prompt.getPrompt();
        if (productId != null) {
            message = "the product id is " + productId +", " + message ;
        }

        User user=new User();
        if(jwt!=null)
            user=userService.findUserProfileByJwt(jwt);

//        Long userId;
//        if(user!=null){
//            userId=user.getId();
//        }

        ApiResponse apiResponse = aiChatBotService.aiChatBot(message,productId,user.getId());

        return ResponseEntity.ok(apiResponse);

    }

}
