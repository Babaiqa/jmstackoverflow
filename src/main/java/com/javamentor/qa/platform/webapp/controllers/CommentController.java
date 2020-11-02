package com.javamentor.qa.platform.webapp.controllers;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/comment/")
///api/comment/question/{questionId}
public class CommentController {

    @PostMapping(value = "question/{questionId}", params = {"page", "size"})
    @ApiOperation(value = "Add comment to question and return CommentDto")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Tags were added", response = String.class),
            @ApiResponse(code = 400, message = "Question not found", response = String.class)
    })
    public ResponseEntity<?> addCommentToQuestion(@PathVariable Long questionId, @RequestParam Long userId,
                                                  @RequestBody String commentText){




    }
}
