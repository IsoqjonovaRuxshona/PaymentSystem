package org.example.repository;

import org.example.model.Card;
import org.example.model.Commission;
import org.example.service.CommissionService;

public class CommissionRepository extends BaseRepository<Commission> {
    private static final CommissionRepository commissionRepository = new CommissionRepository();

    public static CommissionRepository getInstance() {
        return commissionRepository;
    }
    public CommissionRepository() {
        super.path = "src/main/resources/commissions.json";
        super.type = Commission.class;
    }

}
