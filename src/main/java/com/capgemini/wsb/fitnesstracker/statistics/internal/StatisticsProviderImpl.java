package com.capgemini.wsb.fitnesstracker.statistics.internal;

import com.capgemini.wsb.fitnesstracker.statistics.api.Statistics;
import com.capgemini.wsb.fitnesstracker.statistics.api.StatisticsProvider;
import com.capgemini.wsb.fitnesstracker.statistics.api.StatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatisticsProviderImpl implements StatisticsProvider {

    private final StatisticsRepository statisticsRepository;

    @Override
    public Optional<Statistics> getStatistics(Long statisticsId) {
        return statisticsRepository.findById(statisticsId);
    }
}
