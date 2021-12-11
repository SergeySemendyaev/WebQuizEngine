package engine;

import engine.completions.Completion;
import engine.completions.CompletionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class Controller {
    private final Result correct = new Result(true, "Good!");
    private final Result wrong = new Result(false, "Bad!");
    @Autowired
    Service service;
    @Autowired
    CompletionService completionService;

    @GetMapping("/api/quizzes")
    public ResponseEntity<Page<Quiz>> getQuizzes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.ASC,sortBy));
        return new ResponseEntity<>(service.findAll(pageable), HttpStatus.OK);
    }

    @PostMapping("/api/quizzes")
    public ResponseEntity<Quiz> createQuiz(@RequestBody @Valid Quiz quiz, Authentication auth) {
        quiz.setAuthor(auth.getName());
        service.save(quiz);
        return new ResponseEntity<>(quiz, HttpStatus.OK);
    }

    @GetMapping("/api/quizzes/completed")
    public ResponseEntity<Page<Completion>> getCompleted(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "completedAt") String sortBy,
            Authentication auth
    ) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, sortBy));
        return new ResponseEntity<>(completionService.findCompleted(auth.getName(), pageable), HttpStatus.OK);
    }

    @GetMapping("/api/quizzes/{id}")
    public ResponseEntity<Quiz> getQuiz(@PathVariable long id) {
        Optional<Quiz> data = service.findById(id);
        return new ResponseEntity<>(data.orElse(null), data.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @DeleteMapping("/api/quizzes/{id}")
    public ResponseEntity<Quiz> deleteQuiz(@PathVariable long id, Authentication auth) {
        Optional<Quiz> data = service.findById(id);
        if (data.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        Quiz quiz = data.get();
        if (quiz.getAuthor().equals(auth.getName())) {
            service.delete(quiz);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("api/quizzes/{id}/solve")
    public ResponseEntity<Result> solveQuiz(@PathVariable long id, @RequestBody(required = false) Answer answerBody, Authentication auth) {
        Optional<Quiz> data = service.findById(id);
        if (data.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        boolean answerIsCorrect = data.get().answerIsCorrect(Optional.ofNullable(answerBody.getAnswer()).get());
        if (answerIsCorrect) {
            Completion completion = new Completion();
            completion.setQuizId(id);
            completion.setUserName(auth.getName());
            completionService.save(completion);
            return new ResponseEntity<>(correct, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(wrong, HttpStatus.OK);
        }
    }
}
