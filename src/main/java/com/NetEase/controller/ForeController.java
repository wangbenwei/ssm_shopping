package com.NetEase.controller;

import com.NetEase.pojo.Orders;
import com.NetEase.pojo.Product;
import com.NetEase.pojo.User;
import com.NetEase.service.OrdersService;
import com.NetEase.service.ProductService;
import com.NetEase.service.UserService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("")
public class ForeController {
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    OrdersService ordersService;

    @RequestMapping("admin_product_list")
    public String list(Model model) {
        List<Product> ps = productService.list();
        for (Product p : ps) {
            p.setOrders(ordersService.get(p.getId()));
        }
        model.addAttribute("ps", ps);
        return "admin/listProduct";
    }

    @RequestMapping("unPunchased_list")
    public String unPunchasedList(Model model) {
        List<Product> ps = productService.list();
        for (int i = ps.size() - 1; i >= 0; i--) {
            Orders o = ordersService.get(ps.get(i).getId());
            if (o != null) {
                ps.remove(i);
            }
            model.addAttribute("ps", ps);
        }
        return "admin/listProduct_unPunchased";
    }

    @RequestMapping("detail_product")
    public String product(int pid, Model model) {
        Product p = productService.get(pid);
        p.setOrders(ordersService.get(p.getId()));
        model.addAttribute("p", p);
        return "admin/detailProduct";
    }

//    @RequestMapping(value = "foreLogin", method = RequestMethod.POST)
//    //@ResponseBody
//    public String login(@RequestParam("userName") String username, @RequestParam("password") String password, Model model, HttpSession session) {
//        User user = userService.get(username, password);
//        if (null == user) {
//            model.addAttribute("msg", "账号密码错误");
//            return "admin/login";
//        }
//        session.setAttribute("user", user);
//        return "redirect:admin_product_list";
//
////        if (null == user) {
////            return "error";
////        }
////        session.setAttribute("user", user);
////        return "success";
//    }

    @RequestMapping(value = "foreLogin", method = RequestMethod.POST)
    @ResponseBody
    public String login(HttpSession session, HttpServletRequest request) {
        String username = request.getParameter("userName");
        String password = request.getParameter("password");
        User user = userService.get(username, password);
        session.setAttribute("user", user);
        if (null == user) {
            return "{\"message\":\"fuck\",\"code\":500}";
        } else {
            return "{\"result\":true,\"code\":200}";
        }
    }

//    @RequestMapping(value = "loginUser")
//    public String loginUser(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
//        User user = userService.get(username, password);
//        session.setAttribute("user", user);
//        return "redirect:admin_product_list";
//    }

    @RequestMapping("logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:admin_product_list";
    }

    @RequestMapping("publicSubmit")
    public String publish(Product product, Model model) {
        productService.add(product);
        model.addAttribute("p", product);
        return "admin/publishSuccess";
    }

    @RequestMapping("propertyModify")
    public String propertyModify(Product product, Model model) {
        productService.update(product);
        model.addAttribute("p", product);
        return "admin/editorSuccess";
    }

    @RequestMapping("propertyEditor")
    public String propertyEditor(int pid, Model model) {
        Product p = productService.get(pid);
        model.addAttribute("p", p);
        return "admin/editorPublish";
    }

    @RequestMapping(value = "deleteProduct", method = RequestMethod.POST)
    @ResponseBody
    public String delete(HttpServletRequest request) {
        String pid = request.getParameter("id");
        productService.delete(Integer.parseInt(pid));
        return "{\"result\":true,\"code\":200,\"message\":\"Delete success\"}";
    }

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam(value = "file", required = true) MultipartFile file, HttpServletRequest request,
                         HttpServletResponse response) {
        System.out.println("希望可以成功！");
        String path = request.getSession().getServletContext().getRealPath("uploadFile");
        String fileName = file.getOriginalFilename();
        System.out.println("文件上传路径为：" + path);
        File targetFile = new File(path, fileName);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        // 保存
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //return "{\"name\":\"BeJson\",\"result\":\"uploadFile/0b7b02087bf40ad1b9a2b6a9522c11dfa9ecce32.jpg\",\"page\":88,\"isNonProfit\":true}";
        //return "{\"result\":\"" + path + "\"}";
        //return "{\"name\":\"BeJson\",\"result\":\"" + 123 + "\",\"page\":88,\"isNonProfit\":true}";
        return "{\"name\":\"BeJson\",\"result\":\"uploadFile/" + fileName + "\",\"page\":88,\"isNonProfit\":true}";
    }

    @RequestMapping(value = "settleAccount", method = RequestMethod.POST, headers = {"content-type=application/json"})
    @ResponseBody
    public String settleAccount(@RequestBody String shoppingCartJSON) {
        System.out.println(shoppingCartJSON);
        List<Orders> list = JSON.parseArray(shoppingCartJSON, Orders.class);
        for (Orders o : list) {
            o.setCreateDate(new Date());
            //System.out.println(o.toString());
        }
        List<Orders> ordersList = ordersService.list();
        //System.out.println(ordersList.size());
        for (int i = list.size() - 1; i >= 0; i--) {
            Orders o = list.get(i);
            boolean flag = false;
            for (Orders ol : ordersList) {
                if (o.getPid().intValue() == ol.getPid().intValue()) {
                    ol.setNumber(ol.getNumber() + o.getNumber());
                    ol.setCreateDate(new Date());
                    ordersService.update(ol);
                    flag = true;
                    break;
                }
            }
            if (flag) {
                list.remove(o);
            }
        }
        //System.out.println(list.size());
        for (Orders o : list) {
            o.setCreateDate(new Date());
            ordersService.add(o);
        }
        return "{\"name\":\"BeJson\",\"code\":\"200\",\"page\":88,\"isNonProfit\":true}";
    }

    @RequestMapping("accountPage")
    public String account(Model model) {
        List<Orders> orders = ordersService.list();
        int totalPrice = 0;
        for (Orders o : orders) {
            totalPrice = totalPrice + o.getNumber() * o.getProduct().getPrice();
        }
        model.addAttribute("o", orders);
        model.addAttribute("price", totalPrice);
        return "admin/account";
    }

}
