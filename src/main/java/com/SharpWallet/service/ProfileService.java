package com.SharpWallet.service;
import com.SharpWallet.data.model.Profile;
import com.SharpWallet.dto.request.CreateProfileRequest;
import com.SharpWallet.exception.AccountAlreadyExistException;
import org.springframework.stereotype.Service;



@Service
public interface ProfileService {

    Profile createProfile(CreateProfileRequest request) throws AccountAlreadyExistException;
}
