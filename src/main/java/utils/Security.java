package utils;

import org.mindrot.jbcrypt.BCrypt;

public class Security {

    public static String hashPassword(String password) {

        return BCrypt.hashpw(password, BCrypt.gensalt());

    }

    public static boolean checkPassword(String plainPassword, String hashedPassword) {

        if (hashedPassword == null || !hashedPassword.startsWith("$2a$")) {

        	throw new IllegalArgumentException("El hash en la base de datos no es válido");
        
        }
        
        return BCrypt.checkpw(plainPassword, hashedPassword);
    
    }

}