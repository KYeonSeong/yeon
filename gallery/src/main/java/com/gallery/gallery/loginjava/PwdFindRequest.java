package com.gallery.gallery.loginjava;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PwdFindRequest {
  private String findId;
  private String findEmail;
}
