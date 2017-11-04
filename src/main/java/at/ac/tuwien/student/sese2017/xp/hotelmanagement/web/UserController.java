package at.ac.tuwien.student.sese2017.xp.hotelmanagement.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Laszlo on 03.11.2017 at 16:19.
 */
@Controller
public class UserController {

    @RequestMapping(value = "/public/register")
    public synchronized String register(
            @RequestParam(value = "uName", required = false, defaultValue = "") String name,
            @RequestParam(value = "uPass1", required = false, defaultValue = "") String pass,
            Model model) {
//  TODO: check and persist the user
        model.addAttribute("uName", name);
        model.addAttribute("uPass", pass);
//        model.addAttribute("name", name);
//        System.out.println("now : " + System.currentTimeMillis() + " " + new Date());
//        return "<h1>index</h1>";
        return "/public/register";
    }
}
