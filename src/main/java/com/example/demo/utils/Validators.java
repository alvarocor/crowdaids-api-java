package com.example.demo.utils;

import com.example.demo.models.UserModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validators {

    static Matcher matcher;
    static boolean matchFound;
    static Pattern patternToken = Pattern.compile("/[a-zA-Z0-9\\-_]+?\\.[a-zA-Z0-9\\-_]+?\\.([a-zA-Z0-9\\-_]+)$/", Pattern.CASE_INSENSITIVE );
    static Pattern patternGeneral = Pattern.compile("/\\r?\\n|\\r|\\t| /g", Pattern.CASE_INSENSITIVE );
    static Pattern patternEmail = Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", Pattern.CASE_INSENSITIVE);

    public static void validateId(String id) {
        matcher = patternGeneral.matcher(id);
        matchFound = matcher.find();

        if (!id.getClass().getSimpleName().equals("String")) throw new Error("id is not a string");
        if (id.trim().length() == 0) throw new Error("id is empty or blank");
        if (matchFound) throw new Error("blank spaces around id");
        if (id.length() < 24) throw new Error("id has less than 24 characters");
    }

    public static void  validateToken(String token) {
        matcher = patternToken.matcher(token);
        matchFound = matcher.find();

        if (!token.getClass().getSimpleName().equals("String")) throw new Error("token is not a string");
        if (matchFound) throw new Error("invalid token");
    }

    public static void validateUsername(String username) {
        matcher = patternGeneral.matcher(username);
        matchFound = matcher.find();

        if (!username.getClass().getSimpleName().equals("String")) throw new Error("username is not a string");
        if (username.trim().length() == 0) throw new Error("username is empty or blank");
        if (matchFound) throw new Error("username has blank spaces");
        if (username.length() < 4) throw new Error("username has less than 4 characters");
    }

    public static void validatePassword(String password) {
        matcher = patternGeneral.matcher(password);
        matchFound = matcher.find();

        if (!password.getClass().getSimpleName().equals("String")) throw new Error("password is not a string");
        if (password.trim().length() == 0) throw new Error("password is empty or blank");
        if (matchFound) throw new Error("password has blank spaces");
        if (password.length() < 8) throw new Error("password has less than 8 characters");
    }

    public static void validateOldPassword(String oldPassword) {
        matcher = patternGeneral.matcher(oldPassword);
        matchFound = matcher.find();

        if (!oldPassword.getClass().getSimpleName().equals("String")) throw new Error("old password is not a string");
        if (oldPassword.trim().length() == 0) throw new Error("old password is empty or blank");
        if (matchFound) throw new Error("old password has blank spaces");
        if (oldPassword.length() < 8) throw new Error("old password has less than 8 characters");
    }

    public static void validateFullname(String fullname) {
        if (!fullname.getClass().getSimpleName().equals("String")) throw new Error("name is not a string");
        if (fullname.trim().length() == 0) throw new Error("name is empty or blank");
    }

    public static void validateEmail(String email) {
        matcher = patternEmail.matcher(email);
        matchFound = matcher.find();

        if (!email.getClass().getSimpleName().equals("String")) throw new Error("email is not a string");
        if (email.trim().length() == 0) throw new Error("email is empty or blank");
        if (matchFound) throw new Error("is not an e-mail");
    }

    public static void validationData(UserModel data) {

        if (!data.getFullname().equals(null)) {
            validateFullname(data.getFullname());
        }

        if (!data.getEmail().equals(null)) {
            validateEmail(data.getEmail());
        }

        if (!data.getUsername().equals(null)) {
            validateUsername(data.getUsername());
        }

        if (data.getOldPassword().equals(null) && !data.getPassword().equals(null)) throw new Error("old password is not defined");
        if (data.getPassword().equals(null) && !data.getOldPassword().equals(null)) throw new Error("password is not defined");

        if (!data.getPassword().equals(null)) {
            validatePassword(data.getPassword());
        }

        if (!data.getOldPassword().equals(null)) {
            validateOldPassword(data.getOldPassword());
        }

    }
}
