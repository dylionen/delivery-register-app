package pl.app.delregapp.repositiories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.app.delregapp.models.DB.Localization;

public interface LocalizationRepository extends JpaRepository<Localization, Long> {
    Localization getLocalizationById(Long id);
}
