package com.app.cosmetics.core.branch;

import com.app.cosmetics.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BranchService {

    private final BranchRepository branchRepository;

    public BranchResponse create(BranchRequest request) {
        Branch branch = new Branch();
        branch.setName(request.getName());

        Branch result = branchRepository.save(branch);

        return toResponse(result);
    }

    public BranchResponse findById(long id) {
        Branch branch = branchRepository
                .findById(id)
                .orElseThrow(NotFoundException::new);

        return toResponse(branch);
    }

    public List<BranchResponse> findAll() {
        return branchRepository
                .findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public BranchResponse update(long id, BranchRequest request) {
        Branch branch = branchRepository
                .findById(id)
                .orElseThrow(NotFoundException::new);

        branch.setName(request.getName());

        Branch result = branchRepository.save(branch);

        return toResponse(result);
    }

    private BranchResponse toResponse(Branch result) {
        BranchResponse response = new BranchResponse();
        response.setName(result.getName());

        return response;
    }
}
