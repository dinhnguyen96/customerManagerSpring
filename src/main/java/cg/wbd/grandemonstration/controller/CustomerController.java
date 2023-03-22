package cg.wbd.grandemonstration.controller;

import cg.wbd.grandemonstration.model.Customer;
import cg.wbd.grandemonstration.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value = "/customers", method = RequestMethod.GET)
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/")
    public ModelAndView showList() {
        ModelAndView modelAndView = new ModelAndView();
        List<Customer> customers = customerService.findAll();
        modelAndView.addObject("customers", customers);
        modelAndView.setViewName("list");
        return modelAndView;
    }

    @GetMapping("/customerDetail/{id}")
    public String customerDetail(Model model, @PathVariable("id") Long id)
    {
        Customer customer = customerService.findOne(id);
        model.addAttribute("customer",customer);
        return "info";
    }
    @PostMapping("/customerUpdate")
    public String customerUpdate(@ModelAttribute("customer") Customer customer, Model model)
    {
        Customer customerResult = customerService.findOne(customer.getId());

        if (customerResult.getId() == null)
        {
            customerResult.setId(customer.getId());
            customerResult.setName(customer.getName());
            customerResult.setEmail(customer.getEmail());
            customerResult.setAddress(customer.getAddress());
            customerService.add(customerResult);
            return "redirect:/customers/";
        }
        else
        {
            customerService.update(customerResult);
            return "redirect:/customers/";

        }
    }
    @PostMapping("/customerInsert")
    public String customerInsert(Model model)
    {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "info";
    }
}
