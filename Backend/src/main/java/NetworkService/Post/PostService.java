package NetworkService.Post;

import NetworkService.User.User;
import NetworkService.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

        //// add post
    @PostMapping("/posts")
    public ResponseEntity addPost(@RequestHeader("username") String username , @RequestBody String postBody){
        Optional<User> userFromDB = userRepository.findByUsername(username);

        if (userFromDB.isEmpty())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Post newPost = new Post(userFromDB.get(), postBody);
        postRepository.save(newPost);
        return  ResponseEntity.ok(newPost);
    }
        /// Edit post
    @PutMapping("/posts/{postID}")
    public ResponseEntity editPost(@RequestHeader("username") String username,
                                   @RequestBody String newPostBody, @PathVariable int postID ){
        Optional<User> userFromDB = userRepository.findByUsername(username);
        Optional<Post> postByID = postRepository.findById(postID);

        if(userFromDB.isPresent() && postByID.isPresent()) {
            if (userFromDB.get().getId() == postByID.get().getUser().getId()) {
                Post editedPost = new Post(postID, userFromDB.get(), newPostBody);
                postRepository.save(editedPost);
                return ResponseEntity.ok(editedPost);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
        /// delete post
    @DeleteMapping("/posts/{postID}")
    public ResponseEntity deletePost(@RequestHeader("username") String username, @PathVariable int postID)
    {
        Optional<User> userFromDB = userRepository.findByUsername(username);
        Optional<Post> postFromDB = postRepository.findById(postID);

        if (userFromDB.isPresent() && postFromDB.isPresent()){
            if (userFromDB.get().getId() == postFromDB.get().getUser().getId())
            {
                postRepository.deleteById(postID);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
