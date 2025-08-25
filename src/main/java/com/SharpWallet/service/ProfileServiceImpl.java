package com.SharpWallet.service;

import com.SharpWallet.data.model.Profile;
import com.SharpWallet.data.repository.ProfileRepository;
import com.SharpWallet.dto.request.CreateProfileRequest;
import com.SharpWallet.exception.AccountAlreadyExistException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.SharpWallet.util.ApiUtil.ACCOUNT_ALREADY_EXIST;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public Profile createProfile(CreateProfileRequest request) throws AccountAlreadyExistException {
        if(profileExist(request.getEmail(),request.getPhone())) throw new AccountAlreadyExistException(ACCOUNT_ALREADY_EXIST);
        Profile profile = modelMapper.map(request,Profile.class);
        return profileRepository.save(profile);
    }

    private boolean profileExist(String email, String phone) {
        return profileRepository.findProfileByEmailOrPhone(email, phone).isPresent();
    }
}
