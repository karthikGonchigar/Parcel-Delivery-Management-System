package parcels.demo.controller;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import parcels.demo.entity.Parcels;
import parcels.demo.repo.MyRepository;
import parcels.demo.service.MailService;

@Controller
public class MyController {

    private final MailService mailService;
	
	@Autowired
	private MyRepository repository;

    MyController(MailService mailService) {
        this.mailService = mailService;
    }
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	// TO Order The Parcel
	
	@GetMapping("/book")
    public String book(Model model) {
        model.addAttribute("parcels", new Parcels());
        return "book";
    }

    @PostMapping("/bookparcel")
    public String saveParcel(@Valid @ModelAttribute("parcels") Parcels parcel, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "book";
        }

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 5);
        Date deliveryDate = cal.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = formatter.format(deliveryDate);

        int randomId;
        do {
            randomId = (int)(Math.random() * 9000000) + 1000000;
        } 
        while(repository.existsById(randomId));

        parcel.setId(randomId);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDatePick = parcel.getPick().format(dtf);

        repository.save(parcel);

        model.addAttribute("successBook",
                "Your Parcel has been added successfully, and your parcel ID has been emailed to you");

        String subject = "Parcel Booked Successfully!";
        String message = "Your parcel has been booked.\nYour Parcel ID is: " + randomId +
                "\nPickup Date: " + formattedDatePick +
                "\nDelivery Before: " + formattedDate +
                "\nThank You For Choosing Our Service";

        mailService.sendMail(parcel.getEmail(), subject, message);

        return "home";
    }
    
    
    
    // To View the Parcel Details
    @GetMapping("/track")
    public String trackPage(Model model) {
        return "track";
    }

    @PostMapping("/trackparcel")
    public String trackParcel(@RequestParam int id,@RequestParam String password,Model model) {

        Optional<Parcels> parcelOpt = repository.findById(id);
        
        if (parcelOpt.isEmpty()) {
            model.addAttribute("error", "Invalid Parcel ID!");
            return "track";
        }

        Parcels parcel = parcelOpt.get();

        if (!parcel.getPassword().equals(password)) {
            model.addAttribute("error", "Incorrect Password!");
            return "track";
        }

        model.addAttribute("parcel", parcel);
        return "trackResult";
    }   
    
    
    // for Cancel the Parcel
    @GetMapping("/cancel")
    public String cancelPage() {
        return "cancel"; 
    }

    @PostMapping("/cancelparcel")
    public String cancelParcel(@RequestParam int id, @RequestParam String password,Model model) {

        Optional<Parcels> parcelOpt = repository.findById(id);

        if (parcelOpt.isEmpty()) {
            model.addAttribute("error", "Invalid Parcel ID!");
            return "cancel";
        }

        Parcels parcel = parcelOpt.get();

        if (!parcel.getPassword().equals(password)) {
            model.addAttribute("error", "Incorrect Password!");
            return "cancel";
        }
        
        String subject = "Parcel Deleted Succesfully!";
        String message = "\nYour Parcel ID is: " + parcel.getId() +
                "\nThank You For Choosing Our Service";

        mailService.sendMail(parcel.getEmail(), subject, message);

        repository.delete(parcel);
        model.addAttribute("delete",
                "Your Parcel has been deleted Successfully");

        return "home";
    }

    
    // updation
    @GetMapping("/update")
    public String updatePage() {
        return "updateLogin";
    }
    @PostMapping("/updateverify")
    public String verifyUpdate(@RequestParam int id,
                               @RequestParam String password,
                               Model model) {

        Optional<Parcels> parcelOpt = repository.findById(id);

        if (parcelOpt.isEmpty()) {
            model.addAttribute("error", "Invalid Parcel ID!");
            model.addAttribute("id", id);          // keep entered ID
            model.addAttribute("password", password); // keep entered password
            return "updateLogin";
        }

        Parcels parcel = parcelOpt.get();

        if (!parcel.getPassword().equals(password)) {
            model.addAttribute("error", "Incorrect Password!");
            model.addAttribute("id", id);          
            model.addAttribute("password", password);
            return "updateLogin";
        }

        model.addAttribute("parcels", parcel);
        return "updateForm";
    }

    @PostMapping("/updateparcel")
    public String updateParcel(@Valid @ModelAttribute("parcels") Parcels parcel,
                               BindingResult result,
                               Model model) {

        if (result.hasErrors()) {
            return "updateForm";
        }

        repository.save(parcel);

        model.addAttribute("successUpdate", 
            "Parcel updated successfully!");
        String subject = "Parcel Updated Succesfully!";
        String message = "\nYour Parcel ID is: " + parcel.getId() +
                "\nThank You For Choosing Our Service";

        mailService.sendMail(parcel.getEmail(), subject, message);

        return "home";
    }



    
}