package tool.rental.Domain.UseCases;

import tool.rental.App.Settings;
import tool.rental.Domain.Repositories.CacheRepository;
import tool.rental.Utils.ToastError;

public class LogoutUseCase {
    private final CacheRepository cacheRepository = new CacheRepository();

    public void execute() throws ToastError {
        this.cacheRepository.clearCache();
        Settings.setUser(null);
    }
}
