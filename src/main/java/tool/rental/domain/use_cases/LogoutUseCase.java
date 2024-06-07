package tool.rental.domain.use_cases;

import tool.rental.app.Settings;
import tool.rental.domain.repositories.CacheRepository;
import tool.rental.utils.ToastError;

/**
 * This class represents a use case for user logout functionality.
 */
public class LogoutUseCase {
    private final CacheRepository cacheRepository = new CacheRepository(); // Repository for caching

    /**
     * Executes the use case to logout the current user.
     *
     * @throws ToastError if an error occurs during the execution.
     */
    public void execute() throws ToastError {
        this.cacheRepository.clearCache(); // Clear the cache
        Settings.setUser(null); // Set the current user to null
    }
}

