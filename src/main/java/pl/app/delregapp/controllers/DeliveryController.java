package pl.app.delregapp.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.app.delregapp.models.DB.Order;
import pl.app.delregapp.models.DTO.LocalizationDTO;
import pl.app.delregapp.models.DTO.OrderDTO;
import pl.app.delregapp.modules.accounts.models.DB.User;
import pl.app.delregapp.services.LocalizationService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/delivery")
public class DeliveryController {

    private final LocalizationService localizationService;

    public DeliveryController(LocalizationService localizationService) {
        this.localizationService = localizationService;
    }

    @GetMapping("addpremiseaddress")
    public String addPremiseAddress(Model model) {
        LocalizationDTO localization = new LocalizationDTO();
        model.addAttribute("localization", localization);
        return "layout/delivery/newPremiseAddress";
    }

    @PostMapping("addpremiseaddress")
    public String savePremiseAddress(Model model, LocalizationDTO localization) {
        System.out.println(localization);
        localizationService.saveLocalization(localization);
        model.addAttribute("localization", localization);
        return "redirect:/delivery/list";
    }

    @GetMapping("addorder")
    public String addOrder(Model model) {
        OrderDTO order = new OrderDTO();
        model.addAttribute("order", order);
        return "layout/delivery/newOrder";
    }

    @PostMapping("addorder")
    public String saveOrder(Model model, OrderDTO order) {
        System.out.println(order);
        localizationService.saveOrder(order);
        model.addAttribute("order", order);
        return "redirect:/delivery/list";
    }

    @GetMapping("/list")
    public String getAllEmptyDelivers(Model model) {
        List<Order> orders = localizationService.getAllActiveOrdersWithoutDeliver();
        List<Order> myOrders = localizationService.getUserOrders();
        model.addAttribute("orders", orders);
        model.addAttribute("myOrders", myOrders);
        return "layout/delivery/list";
    }

    @GetMapping("/list/assign/{id}")
    public String assignOrderToDeliver(@PathVariable Long id) {
        localizationService.assignOrderToDeliver(id);
        return "redirect:/delivery/list";
    }

    @GetMapping("/list/cancel/{id}")
    public String cancelOrderToDeliver(@PathVariable Long id) {
        localizationService.cancelOrderToDeliver(id);
        return "redirect:/delivery/list";
    }


    @PreAuthorize("hasRole('ROLE_ADMIN,ROLE_MANAGER')")
    @GetMapping("/managelist")
    public String getManageOrders(Model model) {
        List<Order> orders = localizationService.getAllActiveOrders();
        List<User> delivers = localizationService.getDeliversList();

        model.addAttribute("orders", orders);
        model.addAttribute("delivers", delivers);
        return "layout/delivery/deliveryManage";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN,ROLE_MANAGER')")
    @GetMapping(value = {"/managelist/changedeliver/{orderId}", "/managelist/changedeliver/{orderId}/{deliverId}"})
    public String changeDeliver(@PathVariable Long orderId, @PathVariable Optional<Long> deliverId) {
        localizationService.assignOrderToDeliver(orderId, deliverId);
        return "redirect:/delivery/managelist";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN,ROLE_MANAGER')")
    @GetMapping("/managelist/close/{orderId}")
    public String closeOrder(@PathVariable Long orderId) {
        //todo dodać powód zamknięcia
        localizationService.closeOrder(orderId);
        return "redirect:/delivery/managelist";
    }


}
