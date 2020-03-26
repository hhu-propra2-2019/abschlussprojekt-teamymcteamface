package mops.foren.applicationservices;

import mops.foren.domain.model.Forum;
import mops.foren.domain.model.User;
import mops.foren.domain.repositoryabstraction.IUserRepository;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;

@ApplicationService
public class UserService {
    private IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Checks if a user is already in the db.
     * If the user is not in the db, a new user will be created.
     *
     * @param token the token from keycloak.
     * @return the user form the db.
     */
    public User getUserFromDB(KeycloakAuthenticationToken token) {
        User user = getUserFromToken(token);
        if (checkIfUserIsNotInDB(user)) {
            addNewUser(user);
        }
        return getUser(user);
    }

    private boolean checkIfUserIsNotInDB(User user) {
        return this.userRepository.isUserNotInDB(user);
    }

    private void addNewUser(User user) {
        this.userRepository.addNewUserToDB(user);

    }

    private User getUser(User user) {
        return this.userRepository.getUserFromDB(user);
    }

    public void addForumInUser(User user, Forum forum) {
        this.userRepository.addForumToUser(user, forum);
    }

    private User getUserFromToken(KeycloakAuthenticationToken token) {
        KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
        String name = principal.getName();
        String email = principal.getKeycloakSecurityContext().getIdToken().getEmail();
        User user = User.builder()
                .name(name)
                .email(email)
                .build();
        return user;
    }
}
