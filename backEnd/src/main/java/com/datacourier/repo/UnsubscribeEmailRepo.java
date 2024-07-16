package com.datacourier.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.datacourier.entities.UnsubscribeEmail;
@Repository
public interface UnsubscribeEmailRepo extends JpaRepository<UnsubscribeEmail,Long>{
	
	Optional<UnsubscribeEmail> findByEmail(String email);
}
