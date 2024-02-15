package pl.app.delregapp.controllers;


import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.app.delregapp.models.DB.Localization;
import pl.app.delregapp.models.DTO.PanelDTO;
import pl.app.delregapp.modules.accounts.models.DB.User;
import pl.app.delregapp.modules.accounts.models.DTO.UserDTO;
import pl.app.delregapp.modules.accounts.services.UserService;
import pl.app.delregapp.services.LocalizationService;

import java.util.List;

@Controller
public class LoginController {

    private final UserService userService;
    private final LocalizationService localizationService;

    public LoginController(UserService userService, LocalizationService localizationService) {
        this.userService = userService;
        this.localizationService = localizationService;
    }

    @GetMapping("/login")
    public String login() {
        return "layout/user/login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDTO user = new UserDTO();
        model.addAttribute("user", user);
        return "layout/user/register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDTO userDto,
                               BindingResult result,
                               Model model) {
        User existingUser = userService.findUserByLogin(userDto.getName());

        if (existingUser != null && existingUser.getName() != null && !existingUser.getName().isEmpty()) {
            result.rejectValue("name", null,
                    "Istnieje już konto o takiej nazwie");
        }

        if (!userDto.getPassword().equals(userDto.getRepeatPassword())) {
            result.rejectValue("repeatPassword", null,
                    "Hasła się różnią");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "layout/user/register";
        }

        userService.saveUser(userDto);
        return "redirect:/login?success";
    }

    @GetMapping("/panel")
    public String getPanel(Model model) {
        List<Localization> localizations = localizationService.getMyLocalizations();
        User user = userService.getMyUser();
        PanelDTO panel = new PanelDTO();

        panel.setActualLocalization(user.getLocalization());

        model.addAttribute("localizations", localizations);
        model.addAttribute("panel", panel);

        return "layout/user/panel";
    }

    @PostMapping("/panel")
    public String setPanelSettings(Model model, PanelDTO panel){
        userService.saveConfiguration(panel);

        return "redirect:panel";
    }


}