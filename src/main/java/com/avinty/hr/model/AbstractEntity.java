package com.avinty.hr.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractEntity {

  public LocalDateTime createdAt;
  public Long createdBy;
  public LocalDateTime modifiedAt;
  public Long modifiedBy;

}
