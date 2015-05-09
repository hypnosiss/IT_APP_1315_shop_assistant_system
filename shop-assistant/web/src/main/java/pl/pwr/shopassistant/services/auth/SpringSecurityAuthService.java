package pl.pwr.shopassistant.services.auth;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.pwr.shopassistant.dao.UserDao;
import pl.pwr.shopassistant.entities.User;

@Service
public class SpringSecurityAuthService implements AuthService {
    private final static Logger LOGGER = Logger.getLogger(SpringSecurityAuthService.class.getName());

    private User currentUser;

    @Autowired
    private UserDao userDao;

    public boolean isAuthenticated() {
        return this.getCurrentUser() != null;
    }

    public boolean isAnonymous() {
        return !this.isAuthenticated();
    }

    public User getCurrentUser() {
        currentUser = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            currentUser = userDao.findByUsername(authentication.getName());
        } catch (Exception ignored) {

        }

        return currentUser;
    }
}
