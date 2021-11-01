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
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("post", posts);
        model.addAttribute("title","MySpace");
        return "home";
    }

    @GetMapping("/post/add")
    public String postAdd(Model model) {
        model.addAttribute("title","Добавить пост");
        return "post-add";
    }

    @PostMapping("/post/add")
    public String setPost(@RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model){
        Post post = new Post(title,anons,full_text);
        postRepository.save(post);
        return "redirect:/";
    }

    @GetMapping("/post/{id}")
    public String postDetail(@PathVariable(value = "id") long id, Model model){
        if(!postRepository.existsById(id)){
            return "redirect:/";
        }
        Optional<Post> post = postRepository.findById(id);
        Post postInfo = postRepository.findById(id).orElseThrow();
        ArrayList<Post> res = new ArrayList();
        post.ifPresent(res::add);
        model.addAttribute("post",res);
        model.addAttribute("title",postInfo.getTitle());
        return "post-details";
    }

    @GetMapping("/post/{id}/edit")
    public String postEdit(@PathVariable(value = "id") long id, Model model){
        if(!postRepository.existsById(id)){
            return "redirect:/";
        }
        Optional<Post> post = postRepository.findById(id);
        Post postInfo = postRepository.findById(id).orElseThrow();
        ArrayList<Post> res = new ArrayList();
        post.ifPresent(res::add);
        model.addAttribute("post",res);
        model.addAttribute("title",postInfo.getTitle());
        return "post-edit";
    }

    @PostMapping("/post/{id}/edit")
    public String postUpdate(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model){
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        postRepository.save(post);

        return "redirect:/post/{id}";
    }

    @PostMapping("/post/{id}/remove")
    public String postRemove(@PathVariable(value = "id") long id, Model model){
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/";
    }
}
