package com.jb.couponsproject.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClientDetails {
    private String email;
    private String password;
    private ClientType clientType;
}
