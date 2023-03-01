package com.avinty.hr.repository;

import com.avinty.hr.model.Employee;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  @Query(value = "SELECT e FROM Employee e"
      + " WHERE (:name IS NULL OR lower(e.fullName) LIKE concat('%', lower(:name), '%'))"
      + "   AND (:email IS NULL OR lower(e.email) LIKE concat('%', lower(:email), '%')) ORDER BY e.fullName ASC")
  List<Employee> findByNameAndEmailLike(String name, String email);

}
