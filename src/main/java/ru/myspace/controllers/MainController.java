package ru.myspace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.myspace.models.Post;
import ru.myspace.repository.PostRepository;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/") // отслеживаемая ссылка
    public String home(Model model) {
        Iterable<Post> lessons = postRepository.findAll();
        model.addAttribute("lesson", lessons);
        return "home";
    }

    @GetMapping("/lesson/add") // отслеживаемая ссылка
    public String lessonAdd(Model model) {
        return "lesson-add";
    }

    @PostMapping("/lesson/add")
    public String lessonPost(@RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model){
        Post lesson = new Post(title,anons,full_text);
        postRepository.save(lesson);
        return "redirect:/";
    }

    @GetMapping("/lesson/{id}") // отслеживаемая ссылка
    public String lessonDetail(@PathVariable(value = "id") long id, Model model){
        if(!postRepository.existsById(id)){
            return "redirect:/";
        }
        Optional<Post> lesson = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList();
        lesson.ifPresent(res::add);
        model.addAttribute("lesson",res);
        return "lesson-details";
    }

    @GetMapping("/lesson/{id}/edit") // отслеживаемая ссылка
    public String lessonEdit(@PathVariable(value = "id") long id, Model model){
        if(!postRepository.existsById(id)){
            return "redirect:/";
        }
        Optional<Post> lesson = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList();
        lesson.ifPresent(res::add);
        model.addAttribute("lesson",res);
        return "lesson-edit";
    }

    @PostMapping("/lesson/{id}/edit") // отслеживаемая ссылка
    public String lessonUpdate(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model){
        Post lesson = postRepository.findById(id).orElseThrow();
        lesson.setTitle(title);
        lesson.setAnons(anons);
        lesson.setFull_text(full_text);
        postRepository.save(lesson);

        return "redirect:/lesson/{id}";
    }

    @PostMapping("/lesson/{id}/remove") // отслеживаемая ссылка
    public String lessonRemove(@PathVariable(value = "id") long id, Model model){
        Post lesson = postRepository.findById(id).orElseThrow();
        postRepository.delete(lesson);
        return "redirect:/";
    }
}
