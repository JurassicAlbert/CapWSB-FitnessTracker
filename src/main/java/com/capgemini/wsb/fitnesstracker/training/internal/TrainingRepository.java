package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TrainingRepository extends JpaRepository<Training, Long> {

    @Query("SELECT COUNT(t) > 0 FROM Training t WHERE t.user.id = :userId")
    boolean existsByUserId(@Param("userId") Long userId);
}