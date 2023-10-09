package com.zerobase.storereserve.repository;

import com.zerobase.storereserve.domain.Review;
import com.zerobase.storereserve.domain.Store;
import com.zerobase.storereserve.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<List<Review>> findAllByUser(User user);

    Optional<List<Review>> findAllByStore(Store store);
}
