package net.epam.study.service.impl;

import net.epam.study.Constants;
import net.epam.study.service.PaginationService;


public class PaginationImpl implements PaginationService {
    @Override
    public int getActualLimit(int limit) {

        return limit + Constants.DEFAULT_LIMIT;
    }

    @Override
    public int getPreviousLimit(int limit) {

        return limit - Constants.DEFAULT_LIMIT;
    }
}
