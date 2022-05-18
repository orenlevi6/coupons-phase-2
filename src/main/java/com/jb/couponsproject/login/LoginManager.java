package com.jb.couponsproject.login;

import com.jb.couponsproject.exceptions.LoginException;
import com.jb.couponsproject.services.ClientService;
import com.jb.couponsproject.services.serviceImpl.AdminServiceImpl;
import com.jb.couponsproject.services.serviceImpl.CompanyServiceImpl;
import com.jb.couponsproject.services.serviceImpl.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginManager {
    private final AdminServiceImpl adminService;
    private final CompanyServiceImpl companyService;
    private final CustomerServiceImpl customerService;

    public ClientService login(ClientDetails clientDetails) throws LoginException {
        ClientService clientService = null;
        switch (clientDetails.getClientType()) {
            case ADMIN:
                clientService = adminService;
                break;
            case COMPANY:
                clientService = companyService;
                break;
            case CUSTOMER:
                clientService = customerService;
        }
        if (clientService.login(clientDetails.getEmail(), clientDetails.getPassword())) {
            System.out.println("Welcome, " + clientDetails.getClientType().toString());
            return clientService;
        }
        throw new LoginException("Invalid login, please try again");
    }

}
