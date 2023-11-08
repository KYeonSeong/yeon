package com.gallery.gallery.loginjava;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IdFindRequest {
  private String findTel;
  private String findEmail;
}
