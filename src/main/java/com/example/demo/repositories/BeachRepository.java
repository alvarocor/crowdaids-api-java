package com.example.demo.repositories;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Repository
public interface BeachRepository {

    List retrieveSwellConditions(String id);
}
