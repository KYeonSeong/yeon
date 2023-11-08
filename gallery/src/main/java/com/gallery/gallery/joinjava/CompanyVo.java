package com.gallery.gallery.joinjava;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyVo {
  String id, company, pwd, email, tel;

  public void setId(String id){
    this.id = id;
  }
}
