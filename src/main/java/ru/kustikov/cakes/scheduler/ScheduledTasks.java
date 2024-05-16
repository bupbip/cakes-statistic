package ru.kustikov.cakes.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.kustikov.cakes.statistics.Statistic;
import ru.kustikov.cakes.statistics.StatisticService;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledTasks {
    final String messageTemplate = """
            За предыдущий месяц Вы заработали: %d.
            И потратили: %d.
            Это позволило Вам получить прибыль в размере %d!
            Количество выполненных заказов: %d.
            Количество довольных клиентов: %d.



            С любовью, твой Cakes<3""";

    private final SendMailService sendMailService;
    private final StatisticService statisticService;

    // Метод будет вызываться в 00:00:00 первого числа каждого месяца
    @Scheduled(cron = "0 0 0 1 * ?")
    public void sendMonthlyEmail() {
        List<Statistic> statisticToSend = statisticService.getMonthStatistic(LocalDate.now().getMonthValue());

        for (Statistic statistic : statisticToSend) {
            String email = statistic.getUserEmail();

            String message = messageTemplate.formatted(
                    statistic.getIncome(),
                    statistic.getExpences(),
                    statistic.getProfit(),
                    statistic.getOrderCount(),
                    statistic.getCustomerCount()
            );

            boolean success = sendMailService.send(email, message);
            if (success) {
                log.info("Message sent to " + email);
            } else {
                System.out.println("Failed to send message to " + email);
            }
        }
    }
}