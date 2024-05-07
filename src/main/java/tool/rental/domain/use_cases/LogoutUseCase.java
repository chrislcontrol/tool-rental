package tool.rental.domain.use_cases;

import tool.rental.app.Settings;
import tool.rental.domain.repositories.CacheRepository;
import tool.rental.utils.ToastError;

public class LogoutUseCase {
    private final CacheRepository cacheRepository = new CacheRepository();

    public void execute() throws ToastError {
        this.cacheRepository.clearCache();
        Settings.setUser(null);
    }
}
