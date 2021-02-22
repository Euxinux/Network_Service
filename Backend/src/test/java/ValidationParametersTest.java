import NetworkService.User.UserDTO;
import NetworkService.User.ValidationParameters;
import org.junit.Assert;
import org.junit.Test;

public class ValidationParametersTest {

    @Test
    public void validationParameters_emptyUsername_false(){
        //given
         String username = "";
         String password = "password";
         String repeatedPassword = "password";
         UserDTO userDTO = new UserDTO(username,password,repeatedPassword);
         ValidationParameters validationParameters = new ValidationParameters().validation(userDTO);

         //when
         String sResult = validationParameters.getErrorCode();
         boolean result = validationParameters.isCorrect();

         // then
        Assert.assertFalse(result);
        Assert.assertEquals(sResult,"Login or password cannot be empty!");

    }
    @Test
    public void validationParameters_usernameEqualsPassword_false(){
        //given
        String username = "password";
        String password = "password";
        String repeatedPassword = "password";
        UserDTO userDTO = new UserDTO(username,password,repeatedPassword);
        ValidationParameters validationParameters = new ValidationParameters().validation(userDTO);

        //when
        String sResult = validationParameters.getErrorCode();
        boolean result = validationParameters.isCorrect();

        // then
        Assert.assertFalse(result);
        Assert.assertEquals(sResult,"Login or password cannot be the same!");

    }
    @Test
    public void validationParameters_tooShortLogin_false(){
        //given
        String username = "user";
        String password = "password";
        String repeatedPassword = "password";
        UserDTO userDTO = new UserDTO(username,password,repeatedPassword);
        ValidationParameters validationParameters = new ValidationParameters().validation(userDTO);

        //when
        String sResult = validationParameters.getErrorCode();
        boolean result = validationParameters.isCorrect();

        // then
        Assert.assertFalse(result);
        Assert.assertEquals(sResult,"Login or password must have min 6 max 32 signs!");

    }
    @Test
    public void validationParameters_differentPasswords_false(){
        //given
        String username = "username";
        String password = "password";
        String repeatedPassword = "diffPassword";
        UserDTO userDTO = new UserDTO(username,password,repeatedPassword);
        ValidationParameters validationParameters = new ValidationParameters().validation(userDTO);

        //when
        String sResult = validationParameters.getErrorCode();
        boolean result = validationParameters.isCorrect();

        // then
        Assert.assertFalse(result);
        Assert.assertEquals(sResult,"Passwords must be the same");

    }
    @Test
    public void validationParameters_passwordGOOD_true(){
        //given
        String username = "username";
        String password = "Password";
        String repeatedPassword = "Password";
        UserDTO userDTO = new UserDTO(username,password,repeatedPassword);
        ValidationParameters validationParameters = new ValidationParameters().validation(userDTO);

        //when
        String sResult = validationParameters.getErrorCode();
        boolean result = validationParameters.isCorrect();

        // then
        Assert.assertTrue(result);
        Assert.assertEquals(sResult,"Congratulation, correct registration, password strength is: GOOD");

    }

}
