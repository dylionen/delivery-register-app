package pl.app.delregapp.repositiories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.app.delregapp.models.DB.Localization;
import pl.app.delregapp.models.DB.Order;
import pl.app.delregapp.modules.accounts.models.DB.User;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> getAllByLocalizationAndIsActiveAndDeliverUserIsNullOrderByInsertedDesc(Localization localization, Boolean isActive);
    List<Order> getAllByLocalizationAndIsActiveIsTrueOrderByInsertedDesc(Localization localization);
    List<Order> getAllByLocalizationAndIsActiveAndDeliverUserOrderByInsertedDesc(Localization localization, Boolean isActive, User user);

}
