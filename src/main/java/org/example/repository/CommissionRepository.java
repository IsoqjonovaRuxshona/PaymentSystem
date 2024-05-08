package org.example.repository;

import org.example.model.Commission;
import org.example.service.CommissionService;

import static org.example.controller.Main.commissionService;

public class CommissionRepository extends BaseRepository<Commission> {
    private static CommissionRepository commissionRepository = new CommissionRepository();

    public static CommissionService getInstance() {
        return commissionService;
    }
}
