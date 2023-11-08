package com.gallery.gallery.joinjava;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneralVo {
  String id, name, pwd, email, tel;
  int cash;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  Date birth;

  public void setId(String id){
    this.id = id;
  }
}
