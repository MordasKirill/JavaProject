package net.epam.study.service;

public interface PaginationService {
    int getActualLimit(int limit);

    int getPreviousLimit(int limit);
}
