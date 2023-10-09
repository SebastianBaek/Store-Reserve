package com.zerobase.storereserve.repository;

import com.zerobase.storereserve.domain.Store;
import com.zerobase.storereserve.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<List<Store>> findByStorenameContaining(String storename);

    Optional<List<Store>> findAllByOrderByStorenameAsc();

    Optional<List<Store>> findAllByOrderByRatingDesc();

    @Query("SELECT s, (6371 * acos ( cos ( radians(?1) )" +
            "          * cos( radians( s.lat ) )" +
            "          * cos( radians( s.lng) - radians(?2) )" +
            "          + sin ( radians(?1) ) * sin( radians( s.lat ) )" +
            "       )" +
            "   ) as distance from Store s order by distance asc")
    Optional<List<Store>> findAllOrderByDistanceAsc(Double lat, Double lng);

    Optional<Store> findByUser(User user);
}
