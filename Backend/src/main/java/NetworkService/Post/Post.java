package NetworkService.Post;

import NetworkService.User.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "posts")
@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @NonNull
    private User user;

    @NonNull
    private String body;
}
