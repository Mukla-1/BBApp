package de.cau.inf.se.sopro.network;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;

public class RequestHandler {

    private final WebService isbnService;

    @Inject
    public RequestHandler(WebService isbnService) {
        this.isbnService = isbnService;
    }

}