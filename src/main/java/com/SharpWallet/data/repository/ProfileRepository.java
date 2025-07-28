package com.SharpWallet.data.repository;

import com.SharpWallet.data.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findProfileByEmailOrPhone(String email, String phoneNumber);
}
