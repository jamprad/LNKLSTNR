package controllers;

import play.*;
import play.data.validation.Constraints;
import play.mvc.*;
import play.data.Form;

import views.html.*;

public class Application extends Controller {


    public Result index() {
        Logger.debug("index()...");
        return ok(index.render("Welcome to LNKLSTNR."));
    }

    public static class User {
        @Constraints.Required
        public String email;
        public String password;

        public String validate() {
            if (User.authenticate(email, password) == null){
                return "Invalid email or password";
            }
            return null;
        }

        public static String authenticate(String email, String password){
            return "ok";
        }
    }

    public Result login() {
        Logger.debug("login()...");
        return ok(login.render(Form.form(User.class)));
    }

    public Result authenticate() {
        Logger.debug("authenticate()...");
        Form<User> userForm = Form.form(User.class).bindFromRequest();
        if (userForm.hasErrors()) {
            return badRequest(login.render(userForm));
        } else {
            session().clear();
            session("email", userForm.get().email);
            return redirect(
                    routes.Application.index()
            );
        }
    }



}
