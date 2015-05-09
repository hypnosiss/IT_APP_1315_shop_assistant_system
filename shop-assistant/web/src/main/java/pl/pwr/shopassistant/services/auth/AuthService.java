package pl.pwr.shopassistant.services.auth;

import pl.pwr.shopassistant.entities.User;

public interface AuthService {
    public boolean isAuthenticated();
    public boolean isAnonymous();

    User getCurrentUser();
}
