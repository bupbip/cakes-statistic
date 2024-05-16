package ru.kustikov.cakes.statistics;

import lombok.Data;

@Data
public class Statistic {
    private Long statisticId;

    private Integer year;

    private Integer month;

    private Integer orderCount;

    private Integer customerCount;

    private Long income;

    private Long expences;

    private Long profit;

    private String userEmail;
}
