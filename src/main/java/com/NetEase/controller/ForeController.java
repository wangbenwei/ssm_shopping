package com.NetEase.controller;

import com.NetEase.pojo.Product;
import com.NetEase.pojo.User;
import com.NetEase.service.ProductService;
import com.NetEase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;

@Controller
@RequestMapping("")
public class ForeController {
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;

    @RequestMapping("admin_product_list")
    public String list(Model model) {
        List<Product> ps = productService.list();
        model.addAttribute("ps", ps);
        return "admin/listProduct";
    }

    @RequestMapping("detail_product")
    public String product(int pid, Model model) {
        Product p = productService.get(pid);
        model.addAttribute("p", p);
        return "admin/detailProduct";
    }

    @RequestMapping(value = "foreLogin", method = RequestMethod.POST)
    //@ResponseBody
    public String login(@RequestParam("userName") String username, @RequestParam("password") String password, Model model, HttpSession session) {
        User user = userService.get(username, password);
        if (null == user) {
            model.addAttribute("msg", "账号密码错误");
            return "admin/login";
        }
        session.setAttribute("user", user);
        return "redirect:admin_product_list";

//        if (null == user) {
//            return "error";
//        }
//        session.setAttribute("user", user);
//        return "success";
    }

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

    @RequestMapping("delete")
    public String delete(int pid) {
        productService.delete(pid);
        return "redirect:admin_product_list";
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
}
