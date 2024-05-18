package com.example.bookstore.Controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.bookstore.Entity.Book;
import com.example.bookstore.Entity.Login;
import com.example.bookstore.Service.BookService;
import com.example.bookstore.Service.LoginService;
import com.example.bookstore.Service.MyBookService;


//import ch.qos.logback.core.model.Model;

import com.example.bookstore.Entity.MyBookList;
import com.example.bookstore.Repositry.LogRepo;

@Controller
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private MyBookService myBookService;
	
	@Autowired
    private JavaMailSender javaMailSender;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private LogRepo logRepo;
	
	
	@GetMapping("/")
	public String home() {
		
		return "home";
		
	}
	
	@GetMapping("/book_register")
	public String bookRegister() {
		
		return "bookRegister";
		
	}
	
	@GetMapping("/book_list")
	public ModelAndView bookList() {
		
		List<Book>list = bookService.getAllBook();
		
		return new ModelAndView("bookList","book",list);
		
	}
	
	@PostMapping("/save")
    public String addBook(@RequestBody Book book) {
        bookService.save(book);
       return "redirect:/book_list";
    }
	@GetMapping("/my_book")
	public ModelAndView myBook() {
		
		List<MyBookList>list = myBookService.getAllBook();
		return new ModelAndView("myBook","myBook",list);
	}
	
	
	
	
	 @RequestMapping("/myList/{id}")
	 public String getPayList(@PathVariable("id")int id) {
	  
	 Book b = bookService.getBookById(id);
	 MyBookList mb = new MyBookList(b.getId(),b.getName(),b.getAuthor(),b.getPrice());
	 myBookService.saveMyBook(mb);
	 return "redirect:/my_book";
	  
	 }
	 
	
	@RequestMapping("/myPay/{id}")
	public String getMyList(@PathVariable("id") int id, Model model) {
		
		Book book = bookService.getBookById(id);
		model.addAttribute("book", book);
		
		return "pay";
		
	}
	
	@RequestMapping("/delete/{id}")
	public String deleteBookList(@PathVariable("id") int id) {
		
		myBookService.deleteByid(id);
		return "redirect:/my_book";
	}
	
	@RequestMapping("/bookList/{id}")
	public String deleteByList(@PathVariable("id") int id) {
		
		bookService.deleteByid(id);
		return "redirect:/book_list";
	}
	
	@RequestMapping("/editBook/{id}")
	public String editBook(@PathVariable("id") int id,Model model) {
		
		Book b = bookService.getBookById(id);
		model.addAttribute("book",b);
		
		return "bookEdit";
	}
	
	@GetMapping("/contact")
	public String sentGmail() {
		
		return "contact";
	}
	
	@PostMapping("/send-email")
    public String sendEmail(@RequestParam("email") String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Test Subject");
        message.setText("Thank for your feedback.");

        javaMailSender.send(message);

        return "redirect:/book_list"; // Redirect to a success page
    }
	
	@GetMapping("/registration")
	public String registrationPage() {
		
		return "regi";
	}
	
	@PostMapping("/register")
	public String register(@ModelAttribute Login login) {
		
		
		loginService.saveMyBook(login);
		
		return "redirect:/";
		
		
	}
	
	@GetMapping("/login")
	public String loginPage() {
		
		return "login";
	}
	
	
	@PostMapping("/login")
	//@ResponseBody
	
	public String loginProcess(@RequestParam("userName") String userName, @RequestParam("password") String password) {
		
		Login login = logRepo.findByUserName(userName);
		//User dbPassword = userRepo.findByPassword(password);
		//Boolean isPasswordMatch = BCrypt.checkpw(password,dbUser.getPassword());
		
		if (login != null) {
		    boolean isPasswordMatch = password.equals(login.getPassword());

		    if (isPasswordMatch) {
		        return "redirect:/";
		    } else {
		        return "redirect:/login";
		    }
		} else {
		    // Handle the case where the username doesn't exist
		    return "redirect:/login"; // Or display an error message
		}
		
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
