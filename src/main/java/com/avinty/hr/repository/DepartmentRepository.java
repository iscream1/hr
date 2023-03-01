package com.avinty.hr.repository;

import com.avinty.hr.model.Department;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

  @Query(value = "SELECT e FROM Department e"
      + " WHERE (:name IS NULL OR lower(e.name) LIKE concat('%', lower(:name), '%')) ORDER BY e.name ASC")
  List<Department> findByNameLike(String name);

}
