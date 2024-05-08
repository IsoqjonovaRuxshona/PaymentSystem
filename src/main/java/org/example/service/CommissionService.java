package org.example.service;

import org.example.enumerator.CardRole;
import org.example.exception.DataNotFoundException;
import org.example.model.Commission;
import org.example.repository.CommissionRepository;

import java.util.Objects;

import static org.example.controller.Main.commissionService;

public class CommissionService extends BaseService<Commission, CommissionRepository> {
    protected CommissionService(CommissionRepository repository) {
        super(repository);
    }
    public static CommissionService getInstance() {
        return commissionService;
    }
    @Override
    public boolean check(Commission commission) throws DataNotFoundException {
        return false;
    }

    public Double getByRoles(CardRole cardRole, CardRole cardRole1) {
        return repository.getAll().stream().filter(commission ->
                Objects.equals(commission.getSenderCardRole(),cardRole)
        && Objects.equals(commission.getReseiverCardRole(),cardRole1)).findAny().get().getCommission();
    }
}