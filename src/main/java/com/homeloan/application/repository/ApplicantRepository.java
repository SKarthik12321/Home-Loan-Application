package com.homeloan.application.repository;

import com.homeloan.application.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {

    Optional<Applicant> findByEmailIgnoreCase(String email);
}
