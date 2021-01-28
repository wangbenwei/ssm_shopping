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

    //商城首页展示页面（对应：WEB-INF\jsp\admin\listProduct.jsp）
    //展示所有的商品。取出所有商品的信息，并将每个商品对应的订单信息注入到商品对象中（为了在首页展示是否售出）
    //最后将所有商品对象返回给前台
    @RequestMapping("admin_product_list")
    public String list(Model model) {
        List<Product> ps = productService.list();
        for (Product p : ps) {
            p.setOrders(ordersService.get(p.getId()));
        }
        model.addAttribute("ps", ps);
        return "admin/listProduct";
    }

    //展示页面中的《未购买的内容》展示
    //和展示页面一样，首先需要取出所有商品，然后看每个商品是否存在订单，把所有不存在订单的商品返回给前台
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

    //商品的详情页（对应：WEB-INF\jsp\admin\detailProduct.jsp）
    //根据前台传过来的商品id找到找个商品的详细信息注入到product对象中
    //并将这个商品对应的订单信息也注入到product对象中（目的是在卖家查看的时候显示卖出的数量）
    @RequestMapping("detail_product")
    public String product(int pid, Model model) {
        Product p = productService.get(pid);
        p.setOrders(ordersService.get(p.getId()));
        model.addAttribute("p", p);
        return "admin/detailProduct";
    }

    //登陆界面（对应：WEB-INF\jsp\admin\login.jsp）
    //从前台拿到用户名和经过md5加密的密码，去数据库中查找是否存在同时符合用户名和密码的用户
    //如果有的话就将这个用户信息取出来注入到user对象中，如果没有则user对象是一个null，然后将user存在session中
    //最后进行判断，如果存在对象说明用户名密码正确，则返回code为200，否则返回code为500，前台会根据json中的code来判断是否登陆成功
    @RequestMapping(value = "foreLogin", method = RequestMethod.POST)
    @ResponseBody
    public String login(HttpSession session, HttpServletRequest request) {
        String username = request.getParameter("userName");
        String password = request.getParameter("password");
        User user = userService.get(username, password);
        session.setAttribute("user", user);
        if (null == user) {
            return "{\"code\":500}";
        } else {
            return "{\"result\":true,\"code\":200}";
        }
    }

    //登出
    //从session中移除user对象，然后重定向到商城首页
    @RequestMapping("logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:admin_product_list";
    }

    //卖家进行新商品发布（对应:WEB-INF\jsp\admin\publish.jsp）
    //从前台传来的新发布商品的各个信息（包括名称，摘要，图片地址，正文，价格）注入到形参product对象中，然后存入数据库
    //然后将这个product存入到model中返回前台，带着这个product对象的目的是在发布成功页面有一个查看当前发布对象的超链接，需要这个product对象的pid
    @RequestMapping("publicSubmit")
    public String publish(Product product, Model model) {
        productService.add(product);
        model.addAttribute("p", product);
        return "admin/publishSuccess";
    }

    //对应商品详情页的编辑按钮
    //因为对商品详情进行编辑，需要预先在编辑界面展示原商品信息，所以需要根据前台传过来的pid获得对应的product对象，并传回给前台
    @RequestMapping("propertyEditor")
    public String propertyEditor(int pid, Model model) {
        Product p = productService.get(pid);
        model.addAttribute("p", p);
        return "admin/editorPublish";
    }

    //对编辑好的商品详情进行保存（对应：WEB-INF\jsp\admin\editorPublish.jsp）
    //接受修改后的商品详细信息，然后在数据库中进行修改，最后展示修改成功页面
    @RequestMapping("propertyModify")
    public String propertyModify(Product product, Model model) {
        productService.update(product);
        model.addAttribute("p", product);
        return "admin/editorSuccess";
    }

    //卖家登陆后对未售出的商品进行删除
    //根据前台传过来的pid在数据库中进行删除
    //这个功能是需要和前台的ajax配合异步完成
    @RequestMapping(value = "deleteProduct", method = RequestMethod.POST)
    @ResponseBody
    public String delete(HttpServletRequest request) {
        String pid = request.getParameter("id");
        productService.delete(Integer.parseInt(pid));
        return "{\"result\":true,\"code\":200,\"message\":\"Delete success\"}";
    }

    //在商品发布界面通过本地上传进行上传照片
    //（注：在发布界面或者商品详情编辑页面，有两种发布图片的方式：通过输入网络图片地址或者本地上传图片
    // 无论哪种方式都会在点击商品发布时将各个信息注入到product对象中，对于product对象的picture属性，从网络上传保存的是网络图片的url，而通过本地上传保存的是在服务器中保存图片的位置）
    //而将图片保存到服务器中的对应位置就是由下面方法完成的
    //通过MultipartFile接受前台来的文件，然后将其存入服务器的uploadFile文件夹中，并以其名字命名，这个名字和数据库中商品详情中picture存的保持一致
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam(value = "file", required = true) MultipartFile file, HttpServletRequest request,
                         HttpServletResponse response) {
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
        //return "{\"name\":\"BeJson\",\"result\":\"uploadFile/" + fileName + "\",\"page\":88,\"isNonProfit\":true}";
        return "{\"result\":\"uploadFile/" + fileName + "\"}";
    }

    //在购物车中点购买后都会放入这里
    //形参接受到的是有前台传来的json形式的数据，里面包含了商品的pid和加入到购物车的数量,然后将其转换为order对象，并设置其createDate为现在的时间
    //每个order对象对应一个商品，如果这个商品被购买过则就创建一个order对象，第二次购买时就更新这个order中的createDate属性
    //从数据库中取出所有order，然后和前台传过来的order进行比较，如果pid相同就说明原来够买过这个商品，需要增加其数量为原有数量+这次购买的数量，并修改其createDate属性
    //如果前台传过来的order不存在与数据库中，说明之前没买过这个商品，就需要将新的order存入到数据库中
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
        return "{\"code\":\"200\"}";
    }

    //账目界面
    //从数据库中取出所有order，即已被购买的所有商品，并计算总价格返回给前台
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
