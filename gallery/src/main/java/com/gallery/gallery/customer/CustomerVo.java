package com.gallery.gallery.customer;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerVo {
    String id,name,pwd,email,birth,tel,pwdView;
    int cash,auth;   
    
}
