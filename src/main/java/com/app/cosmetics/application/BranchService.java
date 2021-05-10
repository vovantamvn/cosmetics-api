package com.app.cosmetics.application;

import com.app.cosmetics.application.data.BranchData;
import com.app.cosmetics.core.branch.Branch;
import com.app.cosmetics.core.branch.BranchRepository;
import com.app.cosmetics.api.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BranchService {

    private final BranchRepository branchRepository;
    private final ModelMapper modelMapper;

    public BranchData create(String name) {
        Branch branch = new Branch(name);

        Branch result = branchRepository.save(branch);

        return toResponse(result);
    }

    public BranchData findById(long id) {
        Branch branch = branchRepository
                .findById(id)
                .orElseThrow(NotFoundException::new);

        return toResponse(branch);
    }

    public List<BranchData> findAll() {
        return branchRepository
                .findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public BranchData update(long id, String name) {
        Branch branch = branchRepository
                .findById(id)
                .orElseThrow(NotFoundException::new);

        branch.update(name);

        Branch result = branchRepository.save(branch);

        return toResponse(result);
    }

    private BranchData toResponse(Branch branch) {
        return modelMapper.map(branch, BranchData.class);
    }
}
