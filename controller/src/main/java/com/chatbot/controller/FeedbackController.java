package com.chatbot.controller;
import com.chatbot.pojo.Feedback;
import com.chatbot.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/API")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/submit_feedback")
    public ResponseEntity<?> submitFeedback(@RequestBody Feedback feedback, HttpServletRequest request) {
        try {
            Feedback savedFeedback = feedbackService.saveFeedback(feedback, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedFeedback);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("提交反馈失败，请稍后重试。");
        }
    }

    @GetMapping("/feedback")
    public ResponseEntity<?> getAllFeedback() {
        try {
            List<Feedback> feedbackList = feedbackService.getAllFeedback();
            return ResponseEntity.ok(feedbackList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("获取反馈失败，请稍后重试。");
        }
    }
}
