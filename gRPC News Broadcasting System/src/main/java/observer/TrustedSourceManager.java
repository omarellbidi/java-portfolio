package observer;

import java.util.HashMap;
import java.util.Map;

/**
 * A helper class to manage trusted news sources.
 * Handles the registration of sources and verifies their credentials during authentication.
 */
public class TrustedSourceManager {
    private Map<String, String> trustedSources = new HashMap<>();

    /**
     * Adds a new trusted source to the system.
     * @param source The name of the source to register.
     * @param hashedPassword The hashed password of the source.
     * @return true if the source is registered successfully, false if it already exists.
     */
    public boolean addSource(String source, String hashedPassword) {
        if (trustedSources.containsKey(source)) return false;
        trustedSources.put(source, hashedPassword);
        return true;
    }

    /**
     * Checks if a source is already registered.
     * @param source The name of the source.
     * @return true if the source is registered, false otherwise.
     */
    public boolean isSourceRegistered(String source) {
        return trustedSources.containsKey(source);
    }

    /**
     * Verifies if a given source's hashed password matches the stored password.
     * @param source The name of the source.
     * @param hashedPassword The hashed password to verify.
     * @return true if the password matches, false otherwise.
     */
    public boolean authenticateSource(String source, String hashedPassword) {
        return hashedPassword.equals(trustedSources.get(source));
    }
}
