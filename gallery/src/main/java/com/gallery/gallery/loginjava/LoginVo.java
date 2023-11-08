package com.gallery.gallery.loginjava;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginVo {
  String id, name, company, pwd, email, tel;
  int cash, auth;
  Date birth;
}
