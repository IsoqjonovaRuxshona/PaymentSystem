package org.example.service;

import org.example.enumerator.CardRole;
import org.example.exception.DataNotFoundException;
import org.example.model.Commission;
import org.example.repository.CommissionRepository;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class CommissionService extends BaseService<Commission, CommissionRepository> {
    private CommissionService(CommissionRepository repository) {
        super(repository);
    }

    public static final CommissionService commissionService = new CommissionService(CommissionRepository.getInstance());
    public static CommissionService getInstance() {
        return commissionService;
    }
    @Override
    public boolean check(Commission commission) throws DataNotFoundException {
        return false;
    }

    public Optional<Commission> getByRoles(CardRole cardRole, CardRole cardRole1) {
        return repository.getAll().stream().filter(commission ->
                Objects.equals(commission.getSenderCardRole(),cardRole)
        && Objects.equals(commission.getReseiverCardRole(),cardRole1)).findAny();
    }
    public ArrayList<Commission> getAll()


















































    {
        return  repository.getAll();
    }
}
