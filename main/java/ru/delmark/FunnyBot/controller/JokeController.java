package ru.delmark.FunnyBot.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.delmark.FunnyBot.model.Joke;
import ru.delmark.FunnyBot.service.JokeService;

import java.util.List;
import java.util.Optional;


// GET /jokes - выдача всех шуток
// GET /jokes/id - выдача шутки с определенным ID
// POST /jokes - создание новой шутки
// PUT /jokes/id - изменение шутки
// DELETE /jokes/id - удаление шутки

@RestController
@AllArgsConstructor
@RequestMapping("/jokes")
public class JokeController {

    private final JokeService jokeService;

    @PostMapping
    public ResponseEntity<Joke> createJoke(@RequestBody Joke joke) {
        return ResponseEntity.ok(jokeService.addJoke(joke));
    }

    @GetMapping
    public ResponseEntity<List<Joke>> getAllJokes() {
        return ResponseEntity.ok(jokeService.getAllJokes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Joke> getJokeById(@PathVariable("id") long id) {
        Optional<Joke> joke = jokeService.getJokebyId(id);
        return joke.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editJoke(@PathVariable("id") long id, @RequestBody Joke jokeForEdit) {
        if (jokeService.editJoke(id, jokeForEdit)) {
            return ResponseEntity.ok("Joke edited!");
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJoke(@PathVariable("id") long id) {
        if (jokeService.deleteJoke(id)) {
            return ResponseEntity.ok("Deleted joke");
        }
        else {
            return ResponseEntity.badRequest().body("Joke not found");
        }
    }
}
