package com.avinty.hr.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class HrException extends RuntimeException {

  private String message;

}
