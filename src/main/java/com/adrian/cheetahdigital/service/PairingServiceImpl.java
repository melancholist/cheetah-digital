package com.adrian.cheetahdigital.service;

import com.adrian.cheetahdigital.model.Recipients;
import com.adrian.cheetahdigital.model.Request;
import com.adrian.cheetahdigital.model.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PairingServiceImpl implements PairingService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final ObjectMapper mapper = new ObjectMapper();

    @Value("classpath:data.json")
    private Resource resource;

    @Override
    public List<Result> getResultList() throws IOException {
        List<Result> results = new ArrayList<>();
        Request request = parseStringToObject();

        //Check for empty and null tags, and filter by tag size > 1
        List<Recipients> filteredList = request.getRecipients()
                .stream()
                .filter(r -> r.getTags() != null && !r.getTags().isEmpty())
                .filter(t -> t.getTags().size() > 1)
                .collect(Collectors.toList());

        if(filteredList.isEmpty()){
            log.error("Filtered list is empty");
            throw new IllegalArgumentException("Filtered list is empty");
        }

        for (Recipients firstIteration : filteredList) {
            for (Recipients secondIteration : filteredList) {
                if (secondIteration.getId() != firstIteration.getId() && !secondIteration.getName().equalsIgnoreCase(firstIteration.getName())) {

                    Set<String> similarity = secondIteration.getTags()
                            .stream()
                            .distinct()
                            .filter(firstIteration.getTags()::contains)
                            .collect(Collectors.toSet());

                    if (!similarity.isEmpty() && similarity.size() > 1) {
                        Result result = new Result(firstIteration.getName(), secondIteration.getName(), new ArrayList<>(similarity));
                        if(!results.contains(result)) {
                            results.add(result);
                        }
                    }

                }
            }
        }

        return results;
    }

    private Request parseStringToObject() throws IOException {
        String data = readFileToString(resource);
        if(data == null || data.isEmpty()){
            log.error("Empty string is returned from the file");
            throw new IllegalArgumentException("Empty string is returned from the file");
        }
        return mapper.readValue(data, Request.class);
    }

    private String readFileToString(Resource resource) throws IOException {
        Path path = Paths.get(resource.getURI());
        if(!Files.exists(path)){
            log.error("File not found, file path = {}", resource.getURI());
            throw new FileNotFoundException("File not found");
        }
        return FileUtils.readFileToString(path.toFile(), StandardCharsets.UTF_8);
    }
}
