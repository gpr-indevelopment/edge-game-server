package com.gpr.edgegameserver.web;

import com.gpr.edgegameserver.videostreamer.InputLag;
import com.gpr.edgegameserver.videostreamer.InputLagRepository;
import org.springframework.stereotype.Repository;

@Repository
public class InputLagDelegateRepository implements InputLagRepository {

    private final InputLagJpaRepository delegate;

    public InputLagDelegateRepository(InputLagJpaRepository delegate) {
        this.delegate = delegate;
    }

    @Override
    public InputLag save(InputLag inputLag) {
        delegate.save(new InputLagEntity(inputLag));
        return inputLag;
    }
}
