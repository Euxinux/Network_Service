package NetworkService.User;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ValidationParameters {
    boolean isCorrect;
    String errorCode;


    public ValidationParameters validation(UserDTO userDTO)
    {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        String repeatedPassword = userDTO.getRepeatedPassword();

        if(username.isEmpty() || password.isEmpty())
            return new ValidationParameters(false,"Login or password cannot be empty!");

        if(!(password.equals(repeatedPassword)))
            return new ValidationParameters(false,"Passwords must be the same");

        if(username.equals(password))
            return new ValidationParameters(false,"Login or password cannot be the same!");

        if((username.length()<6 || username.length() > 32) || (password.length()<6 || password.length() > 32))
            return new ValidationParameters(false,"Login or password must have min 6 max 32 signs!");

        return new ValidationParameters(true, "Congratulation, correct registration, password strength is: " +checkPasswordStrength(password));
    }

    public PasswordEnum checkPasswordStrength(String password)
    {
        int counter = 0;
        boolean isLowerCase = false, isUpperCase = false, isDigit = false, isSpecialSign = false;

        for (int i =0; i < password.length(); i++) {
            char passwordChar = password.charAt(i);

            if (Character.isDigit(passwordChar))
                isDigit = true;
            if (Character.isUpperCase(passwordChar))
                isUpperCase = true;
            if (Character.isLowerCase(passwordChar))
                isLowerCase = true;
            if(Character.isJavaIdentifierPart(passwordChar))
                isSpecialSign = true;
        }
        counter = (isDigit) ? counter + 1 : counter;
        counter = (isUpperCase) ? counter + 1 : counter;
        counter = (isLowerCase) ? counter + 1 : counter;
        counter = (isSpecialSign) ? counter + 1 : counter;

        return switch(counter) {
            case 2 ->  PasswordEnum.FAIR;
            case 3 ->  PasswordEnum.GOOD;
            case 4 ->  PasswordEnum.EXCELLENT;
            default ->   PasswordEnum.POOR;
        };
    }

    public enum PasswordEnum {
        POOR,FAIR,GOOD,EXCELLENT
    }


}
