package com.example.MedicExpress.Service;

import com.example.MedicExpress.Exception.UserDoesNotExistException;
import com.example.MedicExpress.Model.DeliveryDriverEntity;
import com.example.MedicExpress.Model.PharmacyEntity;
import com.example.MedicExpress.Model.UserEntity;
import com.example.MedicExpress.Repository.AdminRepository;
import com.example.MedicExpress.Repository.DeliveryDriverRepository;
import com.example.MedicExpress.Repository.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    @Autowired
    public AdminRepository adminRepository;

    @Autowired
    public PharmacyRepository pharmacyRepository;

    @Autowired
    public DeliveryDriverRepository deliveryDriverRepository;

    public List<UserEntity> getUsers(){
        return adminRepository.findAll();
    }

    public UserEntity getUserById(Long userId){
        return adminRepository.findById(userId).orElseThrow(()->new UserDoesNotExistException(userId));
    }

    public void deleteUserById(Long userId){
        adminRepository.deleteById(userId);
    }

    public void deleteUsers(List<Long> listUsers){
        adminRepository.deleteAllById(listUsers);
    }

    public List<PharmacyEntity> getAllPharmacies() { return pharmacyRepository.findAll();}

    public PharmacyEntity savePharmacy(PharmacyEntity pharmacy) { return pharmacyRepository.save(pharmacy);}

    public void deletePharmacyById(Long id) {
        pharmacyRepository.deleteById(id);
    }

    public List<DeliveryDriverEntity> getAllDeliveryDrivers() {
        return deliveryDriverRepository.findAll();
    }

    public DeliveryDriverEntity saveDeliveryDriver(DeliveryDriverEntity driver) {
        return deliveryDriverRepository.save(driver);
    }

    public void deleteDeliveryDriverById(Long id) {
        deliveryDriverRepository.deleteById(id);
    }
}
