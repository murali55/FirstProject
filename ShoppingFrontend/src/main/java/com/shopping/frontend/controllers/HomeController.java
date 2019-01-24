package com.shopping.frontend.controllers;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shopping.backend.dao.CartItemDAO;
import com.shopping.backend.dao.ProductDAO;
import com.shopping.backend.model.CartItem;
import com.shopping.backend.model.Category;

@Controller
public class HomeController {
	@Autowired
	private CartItemDAO cartItemDao;
	@Autowired
	private ProductDAO productDao;
	public HomeController(){
		System.out.println("homeController bean is created..");
	}
	@RequestMapping("/home")
	public String homePage(@AuthenticationPrincipal Principal principal,HttpSession session){
        List<CartItem> cartItems=null;
    	if(principal!=null){
         cartItems=cartItemDao.getCartItems(principal.getName());
    	session.setAttribute("cartSize",cartItems.size());
    	
    	}
    	List<Category> categories=productDao.getAllCategories();
    	session.setAttribute("categories", categories);
		return "home";
	}
    @RequestMapping("/aboutus")
    public String aboutUs(){
    	
    	return "aboutUs";
    }
    @RequestMapping("/login")
    public String login(){
    	return "login";
    }
    @RequestMapping("/loginerror")
    public String loginError(Model model){
    	model.addAttribute("loginError","Invalid Email/password");
    	return "login";
    }
    @RequestMapping("/logoutsuccess")
    public String logoutSuccess(Model model){
    	model.addAttribute("logoutSuccess","Loggedout Successfully");
    	return "login";
    }
    @RequestMapping(value="/admin/getcategoryform")
	public String getcategoryform(Model model){
		Category c=new Category();
		model.addAttribute("category",c);
		
		return "category";
}
    @RequestMapping(value="/admin/addcategory")
    public String addProduct(@ModelAttribute Category category){
		
		productDao.addCategory(category);
		
		return "redirect:/admin/getproductform";
    }
}

