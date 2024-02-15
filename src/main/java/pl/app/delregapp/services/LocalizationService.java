package pl.app.delregapp.services;

import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.app.delregapp.models.DB.Localization;
import pl.app.delregapp.models.DB.Order;
import pl.app.delregapp.models.DTO.LocalizationDTO;
import pl.app.delregapp.models.DTO.Map.GencodeResult;
import pl.app.delregapp.models.DTO.Matrix.DistanceMatrixResult;
import pl.app.delregapp.models.DTO.OrderDTO;
import pl.app.delregapp.modules.accounts.models.DB.User;
import pl.app.delregapp.modules.accounts.services.UserServiceImpl;
import pl.app.delregapp.repositiories.LocalizationRepository;
import pl.app.delregapp.repositiories.OrderRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LocalizationService {
    private final LocalizationRepository localizationRepository;
    private final OrderRepository orderRepository;
    private final MapService mapService;
    private final UserServiceImpl userService;

    public LocalizationService(LocalizationRepository localizationRepository, OrderRepository orderRepository, MapService mapService, UserServiceImpl userService) {
        this.localizationRepository = localizationRepository;
        this.orderRepository = orderRepository;
        this.mapService = mapService;
        this.userService = userService;
    }

    public List<Localization> getMyLocalizations() {
        return localizationRepository.findAll();
    }

    public void saveLocalization(LocalizationDTO loc) {
        Localization localization = new Localization();
        localization.setAddress(loc.getAddress());
        localization.setCity(loc.getCity());
        localization.setName(loc.getName());
        localization.setFlatNumber(loc.getFlatNumber());
        localization.setHouseNumber(loc.getHouseNumber());
        localization.setZipCode(loc.getZipCode());
        GencodeResult gencode = null;
        try {
            gencode = mapService.getAddressPosition(loc);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        localization.setPosX(gencode.getResult().get(0).getGeometry().getLocation().getLat());
        localization.setPosY(gencode.getResult().get(0).getGeometry().getLocation().getLng());
        localization.setIsActive(true);
        localization.setFormattedAddress(gencode.getResult().get(0).getFormattedAddress());
        localizationRepository.save(localization);
    }

    public List<Order> getAllActiveOrdersWithoutDeliver() {
        User user = userService.getMyUser();
        return orderRepository.getAllByLocalizationAndIsActiveAndDeliverUserIsNullOrderByInsertedDesc(user.getLocalization(), true);
    }

    public List<Order> getUserOrders() {
        User user = userService.getMyUser();
        return orderRepository.getAllByLocalizationAndIsActiveAndDeliverUserOrderByInsertedDesc(user.getLocalization(), true, user);
    }

    public List<Order> getAllActiveOrders() {
        User user = userService.getMyUser();
        return orderRepository.getAllByLocalizationAndIsActiveIsTrueOrderByInsertedDesc(user.getLocalization());
    }

    @Transactional
    public void saveOrder(OrderDTO orderDTO) {
        Order order = new Order();
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByLogin(auth.getName());

        order.setClientName(orderDTO.getClientName());
        order.setPrice(orderDTO.getPrice());
        GencodeResult gencode = null;
        try {
            gencode = mapService.getAddressPosition(orderDTO.getLocalization());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        order.setLocX(gencode.getResult().get(0).getGeometry().getLocation().getLat());
        order.setLocY(gencode.getResult().get(0).getGeometry().getLocation().getLng());
        order.setFormattedAddress(gencode.getResult().get(0).getFormattedAddress());
        Localization localization = user.getLocalization();

        if (localization == null) {
            throw new RuntimeException("Lokalizacja nie została wybrana");
        }

        DistanceMatrixResult distanceMatrixResult = null;
        try {
            distanceMatrixResult = mapService.getDistanceBetweenTwoLocations(localization.getPosX(), localization.getPosY(), order.getLocX(), order.getLocY());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        order.setDistance(distanceMatrixResult.getRows().get(0).getElements().get(0).getDistance().getValue().doubleValue());
        order.setDistanceStr(distanceMatrixResult.getRows().get(0).getElements().get(0).getDistance().getText());
        order.setInserted(LocalDateTime.now());

        order.setLocalization(localization);
        order.setInsertUser(user);
        order.setIsActive(true);

        orderRepository.save(order);
    }

    @Transactional
    public void assignOrderToDeliver(Long orderId) {
        Order order = orderRepository.getReferenceById(orderId);
        order.setDeliverUser(userService.getMyUser());
        order.setAssigned(LocalDateTime.now());
        orderRepository.save(order);
    }

    @Transactional
    public void assignOrderToDeliver(Long orderId, Optional<Long> userId) {
        Order order = orderRepository.getReferenceById(orderId);
        userId.ifPresentOrElse(id -> order.setDeliverUser(userService.getUserById(id)), () -> order.setDeliverUser(null));
        order.setAssigned(LocalDateTime.now());
        orderRepository.save(order);
    }

    @Transactional
    public void cancelOrderToDeliver(Long id) {
        Order order = orderRepository.getReferenceById(id);
        order.setDeliverUser(null);
        order.setAssigned(null);
        orderRepository.save(order);
    }

    public List<User> getDeliversList() {
        return userService.getAllDelivers();
    }

    @Transactional
    public void closeOrder(Long orderId) {
        Order order = orderRepository.getReferenceById(orderId);
        order.setIsActive(false);
        orderRepository.save(order);
    }
}
