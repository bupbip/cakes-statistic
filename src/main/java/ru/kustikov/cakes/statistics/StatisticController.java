package ru.kustikov.cakes.statistics;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/statistic")
@RequiredArgsConstructor
public class StatisticController {

    private final StatisticService statisticService;

    @GetMapping("/get-statistic")
    public ResponseEntity<List<Statistic>> getStatistic(@RequestParam Integer year,
                                                        @RequestParam Long userId) {
        return statisticService.get(year, userId);
    }
}
