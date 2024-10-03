package com.javatpoint.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.javatpoint.model.Customers;
import com.javatpoint.model.Employee;
import com.javatpoint.model.LoginMessage;
import com.javatpoint.model.Policy;
import com.javatpoint.model.Product;
import com.javatpoint.model.User;
import com.javatpoint.repository.ClaimRepository;
import com.javatpoint.repository.PolicyRepository;
import com.javatpoint.repository.UserRepository;
import com.javatpoint.DTO.LoginDTO;
import com.javatpoint.DTO.PasswordChangeRequest;
import com.javatpoint.DTO.UserDTO;
import com.javatpoint.model.Claim;

import com.javatpoint.service.ClaimService;
import com.javatpoint.service.CustomerService;
import com.javatpoint.service.EmployeeService;
import com.javatpoint.service.OtpService;
import com.javatpoint.service.PolicyService;
import com.javatpoint.service.ProductService;
import com.javatpoint.service.UserService;

import Helper.Helper;

//mark class as Controller
@RestController
@CrossOrigin(origins="*")
public class InsuranceController {
//autowire the BooksService class
	
	private static final Logger logger=LoggerFactory.getLogger(InsuranceController.class);
	
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	ClaimService claimService;
	
	@Autowired
	PolicyService policyService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	PolicyRepository policyRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	ClaimRepository claimRepository;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	OtpService otpService;
	
	
	
	@RequestMapping(value = "/api/customers",method = RequestMethod.GET)
    public List<Customers> getAllCustomers(){
    	 List<Customers> customers = customerService.getAllCustomers();
    	    logger.info("Successfully retrieved {} customers.", customers.size());
    	    for (Customers customer : customers) {
    	        logger.info("Customer details: {}", customer.toString());
    	    };
    	    return customers;
    }
    @RequestMapping(value = "/api/customers/{id}",method = RequestMethod.GET)
    public List<Customers> getAllCustomersById(@PathVariable("id")int id){
    	 List<Customers> customersById = customerService.getAllCustomersById(id);
    	 return customersById;
    }
    @RequestMapping(value = "/api/customers/{id}/policies",method = RequestMethod.GET)
    public List<String> getAllCustomersPolicesById(@PathVariable("id")int id){
    	 List<Object[]> customersPolicesById = customerService.getAllCustomersPolicesById(id);
    	 List<String> ans= new ArrayList<String>();
    	 for (Object[] result :customersPolicesById ) {
             Integer customerId = (Integer) result[0];   
             String policy = (String) result[1];
             String formatans = "Customer ID: " + customerId + ", Policy: " + policy;
             ans.add(formatans);
    	 }
    	 return ans;
    }
    @RequestMapping(value = "/api/customers/{id}/claims",method = RequestMethod.GET)
    public List<String> getAllCustomersClaimsById(@PathVariable("id")int id){
    	List<Object[]> customersClaimsById = customerService.getAllCustomersClaimsById(id);
		 List<String> ans= new ArrayList<String>();
    	 try {
			 for (Object[] result :customersClaimsById ) {
			     Integer customerId = (Integer) result[0];   
			     String claim = (String) result[1];
			     String formatans = "Customer ID: " + customerId + ", Claim: " + claim;
			     ans.add(formatans);
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
    	 return ans;
    }
    @RequestMapping(value = "/api/policies",method = RequestMethod.GET)
    public List<Policy> getAllPolicies(){
    	List<Policy> policies = new ArrayList<Policy>();
    	 try {
			policies = policyService.getAllPolices();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	 return policies;
    }
    @RequestMapping(value = "/api/policies/{id}",method = RequestMethod.GET)
    public List<Policy> getAllPoliciesById(@PathVariable("id")int id){
    	List<Policy> policiesById=new ArrayList<Policy>();
    	try {
    		policiesById=policyService.getAllPoliciesById(id);
    	}catch (Exception e) {
    		e.printStackTrace();
    	}
    	return policiesById;
    }
    @RequestMapping(value="/api/create/policies",method = RequestMethod.POST)
	private void savePolicies(@RequestBody Policy policy) {
    	policyService.saveOrUpdatePolicy(policy);	
	}
    @RequestMapping(value="api/create/employee",method=RequestMethod.POST)
    private void saveEmployee(@RequestBody Employee employee) {
    	employeeService.saveOrUpdateEmployee(employee);
    }
    @PutMapping(value="/api/update/policy/{id}")
    public ResponseEntity<Policy> updatePolicy(@PathVariable int id,@RequestBody Policy policy){
    	Policy p = policyService.getPoliciesById(id);
    	if(p==null) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    	}
    	p.setPolicies(policy.getPolicies());
    	Policy updatePolicy = policyRepository.save(p);
    	return ResponseEntity.ok(updatePolicy);	
    }
    @RequestMapping(value = "/api/get/policy/{id}",method = RequestMethod.GET)
    public Policy getPolicyById(@PathVariable("id")int id){
    	 Policy policyById = policyService.getPoliciesById(id);
    	 return policyById;
    }
    
    @RequestMapping(value = "/api/claims",method = RequestMethod.GET)
    public List<Claim> getAllClaims(){
    	 List<Claim> claims = claimService.getAllClaims();
    	 return claims;
    }
    @RequestMapping(value = "/api/claims/{id}",method = RequestMethod.GET)
    public List<Claim> getAllClaimsById(@PathVariable("id")int id){
    	 List<Claim> claimsById = claimService.getAllClaimsById(id);
    	 return claimsById;
    }
    @RequestMapping(value="/api/create/claims",method = RequestMethod.POST)
	private void saveClaim(@RequestBody Claim claims) {
    	claimService.saveOrUpdateClaim(claims);
		
	}
    
    @PutMapping(value="/api/update/claim/{id}")
    public ResponseEntity<Claim>updateClaim(@PathVariable int id,@RequestBody Claim claim ){
    	Claim c = claimService.getClaimaById(id);
    	c.setClaims(claim.getClaims());
    	Claim updateClaim=claimRepository.save(c);
    	return ResponseEntity.ok(updateClaim);
    }
    
   
    
    @RequestMapping(value = "/api/get/claim/{id}",method = RequestMethod.GET)
    public Claim getClaimById(@PathVariable("id")int id){
    	 Claim claimById = claimService.getClaimaById(id);
    	 return claimById;
    }
    
    @RequestMapping(value = "/api/delete/policy/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<Map<String,Boolean>> deletePolicy(@PathVariable("id")int id){
    	Policy p=policyRepository.findById(id).orElseThrow(() -> 
    	new EntityNotFoundException("Policy Not Found " + id));
    	policyRepository.delete(p);
    	Map<String, Boolean> response = new HashMap<String, Boolean>();
    	response.put("Deleted", Boolean.TRUE);
    	return ResponseEntity.ok(response);

    }
    @RequestMapping(value = "/api/delete/claim/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<Map<String,Boolean>> deleteClaim(@PathVariable("id")int id){
    	Claim c=claimRepository.findById(id).orElseThrow(() -> 
    	new EntityNotFoundException("Claim Not Found " + id));
    	claimRepository.delete(c);
    	Map<String, Boolean> response = new HashMap<String, Boolean>();
    	response.put("Deleted", Boolean.TRUE);
    	return ResponseEntity.ok(response);

    }
 
    @RequestMapping(value="/api/employee", method=RequestMethod.GET)
	public List <Employee> getAllEmploye(){
		List<Employee> employee =employeeService.getAllEmployee();
		return employee;
	}
    
    
    @PostMapping("/product/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        if (Helper.checkExcelFormat(file)) {
            this.productService.save(file);
            Map<String, String> response = new HashMap<>();
            response.put("message", "File uploaded successfully");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please correct the file format.");
        }
    }
    
    @GetMapping("/product")
    public List<Product> getAllProduct(){
    	return this.productService.getAllProduct();
    	
    }
//     Spring security
    @RequestMapping(value="/save",method = RequestMethod.POST)
	private ResponseEntity<?> saveUser(@RequestBody UserDTO userdto) {
    	LoginMessage message = userService.addUser(userdto);
    	return ResponseEntity.ok(message);
		
	}
    @RequestMapping(value="/login",method = RequestMethod.POST)
	private ResponseEntity<?> saveLogin(@RequestBody LoginDTO logindto) {
    	boolean msg = userService.updateLoginTime(logindto.getEmail());
    	
    	LoginMessage message = userService.loginUser(logindto);
        if(msg) {
        	return ResponseEntity.ok(message);
    		
    		}
        else {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user not found");
        }
		
	}
    @PostMapping("/request/otp")
    public ResponseEntity<?> requestOtp(@RequestBody Map<String, String> request) {
       String email=request.get("email");
       try {
           otpService.generateAndSendOtp(email);
           return ResponseEntity.ok("Password sent to your email.");
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
       }
    }
    @PostMapping("/verify/otp")
    public ResponseEntity<String> verifyOtp(@RequestBody Map<String, String> request) {
       String email=request.get("email");
       String otp=request.get("otp");
       if(otpService.verifyOtp(email, otp)) {
    	   return ResponseEntity.ok("OTP verified. You can now reset your password.");
       }
       else {
    	   return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired OTP.");
       }
    }
    @PostMapping("/reset/password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> request) {
       String email=request.get("email");
       String newPassword=request.get("newPassword");
       otpService.resetPassword(email, newPassword);
       return ResponseEntity.ok("Password reset succesfully");
    }
    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam String token){
    	User user=userService.findByVerificationToken(token);
    	if(user==null || 
    			user.getVerification_token_expired_time().isBefore(LocalDateTime.now())) {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or Expired Token");
    		
    	}
    	user.setIs_enabled(true);
    	user.setVerification_token(null);
    	user.setVerification_token_expired_time(null);
    	userRepository.save(user);
    	return ResponseEntity.ok("Email Veriefied SuccessFully");
    	
    	
    }
    @PostMapping("/change/password")
    public ResponseEntity<String> changePassword(@RequestBody PasswordChangeRequest passwordChangeRequest) {
        if (!passwordChangeRequest.getNewPassword().equals(passwordChangeRequest.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("New password and confirm password did not match");
        }

        try {
           
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            passwordChangeRequest.getEmail(), 
                            passwordChangeRequest.getCurrentPassword()
                    )
            );
            
            
            if (!authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid current password");
            }

            
            User user = userRepository.findByEmail(passwordChangeRequest.getEmail());
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            
            user.setPassword(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));
            userRepository.save(user);

            return ResponseEntity.ok("Password successfully updated");

        } catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }
    }

    
}
