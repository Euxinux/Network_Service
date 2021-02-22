package NetworkService.User;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
public class UserDTO {
    @NonNull
    private String username;
    @NonNull
    private String password;
    private String repeatedPassword;

}
