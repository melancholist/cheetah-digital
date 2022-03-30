package com.adrian.cheetahdigital.service;

import com.adrian.cheetahdigital.model.Result;

import java.io.IOException;
import java.util.List;

public interface PairingService {

    List<Result> getResultList() throws IOException;
}
